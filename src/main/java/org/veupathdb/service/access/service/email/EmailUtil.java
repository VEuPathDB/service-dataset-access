package org.veupathdb.service.access.service.email;

import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.regex.Pattern;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.Logger;
import org.stringtemplate.v4.ST;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.access.Main;
import org.veupathdb.service.access.model.Dataset;
import org.veupathdb.service.access.model.DatasetEmails;
import org.veupathdb.service.access.model.Email;
import org.veupathdb.service.access.model.EndUserRow;
import org.veupathdb.service.access.repo.DB;
import org.veupathdb.service.access.util.PatternUtil;

public class EmailUtil
{
  private static final char TEMPLATE_DELIM = '$';

  private static final Pattern splitPattern = Pattern.compile("((?:\".+?\"|[^@ ]+)@[^, ]+)(?:,|$)");

  /**
   * Dataset property names of the properties relevant to sending emails.
   */
  private static final String
    EMAIL_TO_PROP   = "requestEmailBcc",
    EMAIL_FROM_PROP = "requestEmail";

  /**
   * Error messages
   */
  private static final String
    ERR_DUP  = "Dataset '%s' has duplicate '%s' properties.",
    ERR_UNK  = "Unrecognized property '%s' in results for dataset '%s'.",
    ERR_MISS = "Dataset '%s' is missing property '%s'.",
    ERR_MULT = "Properties returned for more than one dataset.  Ids retrieved were '%s' and '%s'.";

  private static EmailUtil instance;

  private final Logger log;

  public EmailUtil() {
    log = LogProvider.logger(EmailUtil.class);
  }

  public String populateTemplate(final String tpl, final Dataset dataset, final EndUserRow user) {
    final var inject = new ST(tpl, TEMPLATE_DELIM, TEMPLATE_DELIM);
    populateInjection(inject, dataset);

    inject.add("end-user", user);

    return inject.render();
  }

  public String populateTemplate(final String template, final Dataset dataset) {
    final var inject = new ST(template, TEMPLATE_DELIM, TEMPLATE_DELIM);
    populateInjection(inject, dataset);
    return inject.render();
  }

  public String[] splitEmails(final String emails) {
    return PatternUtil.matchStream(splitPattern, emails, 1).toArray(String[]::new);
  }

  public DatasetEmails resultSetToEmail(final ResultSet rs) throws SQLException {
    String to   = null;
    String from = null;
    String id   = null;

    while (rs.next()) {
      if (id == null) {
        id = rs.getString(DB.Column.DatasetProperties.DatasetId);
      } else {
        var tmp = rs.getString(DB.Column.DatasetProperties.DatasetId);
        if (!id.equals(tmp))
          throw new IllegalStateException(String.format(ERR_MULT, id, tmp));
      }

      var prop = rs.getString(DB.Column.DatasetProperties.Property);

      switch (prop) {
        case EMAIL_TO_PROP -> {
          if (to != null)
            throw new IllegalStateException(String.format(ERR_DUP, id, prop));
          to = rs.getString(DB.Column.DatasetProperties.Value);
        }

        case EMAIL_FROM_PROP -> {
          if (from != null)
            throw new IllegalStateException(String.format(ERR_DUP, id, prop));
          from = rs.getString(DB.Column.DatasetProperties.Value);
        }

        default -> throw new IllegalStateException(String.format(ERR_UNK, prop, id));
      }
    }

    if (to == null)
      throw new IllegalStateException(String.format(ERR_MISS, id, EMAIL_TO_PROP));

    if (from == null)
      throw new IllegalStateException(String.format(ERR_MISS, id, EMAIL_FROM_PROP));

    return new DatasetEmails(to, from);
  }

  public static EmailUtil getInstance() {
    if (instance == null)
      instance = new EmailUtil();

    return instance;
  }


  public Address[] toAddresses(final String[] emails) throws Exception {
    log.trace("EmailUtil#toAddresses(String[])");

    var addresses = new Address[emails.length];

    for (var i = 0; i < emails.length; i++)
         addresses[i] = new InternetAddress(emails[i]);

    return addresses;
  }

  public MimeMessage prepMessage(final Session ses, final Email mail) throws Exception {
    log.trace("EmailUtil#prepMessage(Session, Email)");

    var message = new MimeMessage(ses);

    message.setFrom(mail.getFrom());

    message.setReplyTo(toAddresses(new String[]{Main.config.getSupportEmail()}));
    message.setRecipients(Message.RecipientType.TO, toAddresses(mail.getTo()));
    message.setSubject(mail.getSubject());
    message.setSentDate(new Date());

    if (mail.getCc() != null && mail.getCc().length > 0)
      message.setRecipients(Message.RecipientType.CC, toAddresses(mail.getCc()));
    if (mail.getBcc() != null && mail.getBcc().length > 0)
      message.setRecipients(Message.RecipientType.BCC, toAddresses(mail.getBcc()));

    var bodyPart = new MimeBodyPart();
    bodyPart.setContent(mail.getBody(), "text/plain");

    message.setContent(new MimeMultipart(bodyPart));

    return message;
  }

  void populateInjection(final ST inject, final Dataset ds) {
    inject.add("dataset", ds);
    inject.add("site-url", Main.config.getSiteUrl());
    inject.add("sign-up-link", URI.create(makeUrl(Main.config.getRegistrationPath())));
    inject.add("app-link", URI.create(makeUrl(Main.config.getApplicationPath())));
  }

  String makeUrl(final String path) {
    return Main.config.getSiteUrl() + (path.charAt(0) == '/' ? path : "/" + path);
  }
}

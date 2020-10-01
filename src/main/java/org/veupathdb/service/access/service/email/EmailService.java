package org.veupathdb.service.access.service.email;


import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.access.Main;
import org.veupathdb.service.access.model.Dataset;
import org.veupathdb.service.access.model.DatasetEmails;
import org.veupathdb.service.access.model.Email;
import org.veupathdb.service.access.model.EndUserRow;

public class EmailService
{

  private static EmailService instance;

  private final Logger log;

  public EmailService() {
    log = LogProvider.logger(getClass());
  }

  public static DatasetEmails getDatasetEmails(final String datasetId) throws Exception {
    return EmailRepo.getDatasetEmails(datasetId);
  }

  public void sendEndUserRegistrationEmail(final String address, final Dataset dataset)
  throws Exception {
    final var template = Const.EndUserTemplate;
    final var util     = EmailUtil.getInstance();

    final var email = new Email()
      .setSubject(util.populateTemplate(template.getSubject(), dataset))
      .setBody(util.populateTemplate(template.getBody(), dataset))
      .setFrom(dataset.getProperties().get(Dataset.Property.REQUEST_EMAIL))
      .setTo(new String[]{address});

    sendEmail(email);
  }

  public void sendProviderRegistrationEmail(final String address, final Dataset dataset)
  throws Exception {
    final var template = Const.ProviderTemplate;
    final var util     = EmailUtil.getInstance();

    final var email = new Email()
      .setSubject(util.populateTemplate(template.getSubject(), dataset))
      .setBody(util.populateTemplate(template.getBody(), dataset))
      .setFrom(dataset.getProperties().get(Dataset.Property.REQUEST_EMAIL))
      .setTo(new String[]{address});

    sendEmail(email);
  }

  public void sendEndUserUpdateNotificationEmail(
    final String[] cc,
    final Dataset dataset,
    final EndUserRow user
  ) throws Exception {
    final var template = Const.EditNotification;
    final var util     = EmailUtil.getInstance();

    sendEmail(new Email()
      .setSubject(util.populateTemplate(template.getSubject(), dataset)));
    throw new Exception("unfinished stub");
  }

  public void sendEmail(final Email mail) throws Exception {
    final var util = EmailUtil.getInstance();

    final var props = new Properties();
    props.put("mail.smtp.host", Main.config.getSmtpHost());
    props.put("mail.debug", String.valueOf(Main.config.isEmailDebug()));

    final var session = Session.getInstance(props);

    final var message = util.prepMessage(session, mail);

    message.setFrom(mail.getFrom());
    message.setRecipients(MimeMessage.RecipientType.TO, util.toAddresses(mail.getTo()));
    message.setRecipients(MimeMessage.RecipientType.CC, util.toAddresses(mail.getCc()));
    message.setRecipients(MimeMessage.RecipientType.BCC, util.toAddresses(mail.getBcc()));
    message.setReplyTo(util.toAddresses(new String[]{Main.config.getSupportEmail()}));
    message.setSubject(mail.getSubject());

    final var content = new MimeBodyPart();
    content.setContent(mail.getBody(), "text/plain");
    final var body = new MimeMultipart();
    body.addBodyPart(content);

    message.setContent(body);

    Transport.send(message);
  }

  public static EmailService getInstance() {
    if (instance == null)
      instance = new EmailService();

    return instance;
  }
}

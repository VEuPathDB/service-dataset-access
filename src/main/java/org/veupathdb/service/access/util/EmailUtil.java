package org.veupathdb.service.access.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.access.Main;
import org.veupathdb.service.access.model.Email;

public class EmailUtil
{
  private static final Logger log = LogProvider.logger(EmailUtil.class);

  public static void sendEmail(final Email mail) throws Exception {
    var props = new Properties();
    props.put("mail.smtp.host", Main.config.getSmtpHost());
    props.put("mail.debug", String.valueOf(Main.config.isEmailDebug()));

    var session = Session.getInstance(props);

    var message = prepMessage(session, mail);

    message.setFrom(mail.getFrom());
    message.setRecipients(MimeMessage.RecipientType.TO, toAddresses(mail.getTo()));
    message.setRecipients(MimeMessage.RecipientType.CC, toAddresses(mail.getCc()));
    message.setRecipients(MimeMessage.RecipientType.BCC, toAddresses(mail.getBcc()));
    message.setSubject(mail.getSubject());

    var content = new MimeBodyPart();
    content.setContent(mail.getBody(), "text/plain");
    var body = new MimeMultipart();
    body.addBodyPart(content);

    message.setContent(body);

    Transport.send(message);
  }

  private static Address[] toAddresses(final String[] emails) throws Exception {
    log.trace("EmailUtil#toAddresses(String[])");

    var addresses = new Address[emails.length];

    for (var i = 0; i < emails.length; i++)
      addresses[i] = new InternetAddress(emails[i]);

    return addresses;
  }

  private static MimeMessage prepMessage(final Session ses, final Email mail) throws Exception {
    log.trace("EmailUtil#prepMessage(Session, Email)");

    var message = new MimeMessage(ses);

    message.setFrom(mail.getFrom());

//    message.setReplyTo();
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
}

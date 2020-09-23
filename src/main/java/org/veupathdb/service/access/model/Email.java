package org.veupathdb.service.access.model;

public class Email
{
  private String[] to;

  private String[] cc;

  private String[] bcc;

  private String from;

  private String subject;

  private String body;

  public String[] getTo() {
    return to;
  }

  public Email setTo(String[] to) {
    this.to = to;
    return this;
  }

  public String[] getCc() {
    return cc;
  }

  public Email setCc(String[] cc) {
    this.cc = cc;
    return this;
  }

  public String[] getBcc() {
    return bcc;
  }

  public Email setBcc(String[] bcc) {
    this.bcc = bcc;
    return this;
  }

  public String getFrom() {
    return from;
  }

  public Email setFrom(String from) {
    this.from = from;
    return this;
  }

  public String getSubject() {
    return subject;
  }

  public Email setSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public String getBody() {
    return body;
  }

  public Email setBody(String body) {
    this.body = body;
    return this;
  }
}

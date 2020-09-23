package org.veupathdb.service.access.model;

import org.veupathdb.lib.container.jaxrs.config.Options;
import picocli.CommandLine;

public class Config extends Options
{
  @CommandLine.Option(
    names = "--smtp-host",
    defaultValue = "${env:SMTP_HOST}",
    required = true,
    arity = "1"
  )
  private String smtpHost;

  @CommandLine.Option(
    names = "--mail-debug",
    defaultValue = "${env:EMAIL_DEBUG}",
    arity = "1"
  )
  private boolean emailDebug = false;

  @CommandLine.Option(
    names = "--support-email",
    defaultValue = "${env:SUPPORT_EMAIL}",
    required = true,
    arity = "1"
  )
  private String supportEmail;

  public String getSmtpHost() {
    return smtpHost;
  }

  public boolean isEmailDebug() {
    return emailDebug;
  }

  public String getSupportEmail() {
    return supportEmail;
  }
}

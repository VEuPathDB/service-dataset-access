package org.veupathdb.service.access.controller;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Request;

import org.veupathdb.lib.container.jaxrs.model.User;
import org.veupathdb.lib.container.jaxrs.providers.UserProvider;

public class Util
{
  @SuppressWarnings("FieldMayBeFinal")
  private static Util instance = new Util();

  Util() {}

  public static Util getInstance() {
    return instance;
  }

  public User mustGetUser(final Request req) {
    return UserProvider.lookupUser(req)
      .orElseThrow(InternalServerErrorException::new);
  }

  public static User requireUser(final Request req) {
    return getInstance().mustGetUser(req);
  }
}

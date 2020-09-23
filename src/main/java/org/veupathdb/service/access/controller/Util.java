package org.veupathdb.service.access.controller;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Request;

import org.gusdb.fgputil.accountdb.UserProfile;
import org.veupathdb.lib.container.jaxrs.providers.UserProvider;

public class Util
{
  @SuppressWarnings("FieldMayBeFinal")
  private static Util instance = new Util();

  Util() {}

  private static final String
    errNoDatasetId = "Cannot use this endpoint without a datasetId query param value";

  public static Util getInstance() {
    return instance;
  }

  public void validateDatasetId(final String datasetId) {
    if (datasetId == null || datasetId.isBlank())
      throw new ForbiddenException(errNoDatasetId);
  }

  public static void requireDatasetId(final String datasetId) {
    getInstance().validateDatasetId(datasetId);
  }

  public UserProfile mustGetUser(final Request req) {
    return UserProvider.lookupUser(req)
      .orElseThrow(InternalServerErrorException::new);
  }

  public static UserProfile requireUser(final Request req) {
    return getInstance().mustGetUser(req);
  }
}

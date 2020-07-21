package org.veupathdb.service.access.controller;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Request;

import org.gusdb.fgputil.accountdb.UserProfile;
import org.veupathdb.lib.container.jaxrs.providers.UserProvider;

final class Util
{
  private static final String
    errNoDatasetId = "Cannot use this endpoint without a datasetId query param "
      + "value";

  static void requireDatasetId(final String datasetId) {
    if (datasetId == null || datasetId.isBlank())
      throw new ForbiddenException(errNoDatasetId);
  }

  static UserProfile requireUser(final Request req) {
    return UserProvider.lookupUser(req)
      .orElseThrow(InternalServerErrorException::new);
  }
}

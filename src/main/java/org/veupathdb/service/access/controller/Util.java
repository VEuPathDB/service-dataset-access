package org.veupathdb.service.access.controller;

import javax.ws.rs.ForbiddenException;

final class Util
{
  private static final String
    errNoDatasetId = "Cannot use this endpoint without a datasetId query param "
      + "value";

  static void requireDatasetId(final String datasetId) {
    if (datasetId == null || datasetId.isBlank())
      throw new ForbiddenException(errNoDatasetId);
  }
}

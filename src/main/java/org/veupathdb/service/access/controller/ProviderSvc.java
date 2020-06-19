package org.veupathdb.service.access.controller;

import org.veupathdb.service.access.generated.resources.DatasetProviders;

public class ProviderSvc implements DatasetProviders
{
  @Override
  public GetListResponse getList(String datasetId) {
    return null;
  }

  @Override
  public PostResponse post() {
    return null;
  }

  @Override
  public PutByIdResponse putById(
    int providerId
  ) {
    return null;
  }

  @Override
  public DeleteByIdResponse deleteById(
    int providerId
  ) {
    return null;
  }
}

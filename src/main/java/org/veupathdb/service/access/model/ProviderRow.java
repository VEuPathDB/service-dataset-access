package org.veupathdb.service.access.model;

public class ProviderRow extends PartialProviderRow
{
  private long providerId;

  public long getProviderId() {
    return providerId;
  }

  public void setProviderId(long providerId) {
    this.providerId = providerId;
  }
}

package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("providerId")
public class DatasetProviderCreateResponseImpl implements DatasetProviderCreateResponse {
  @JsonProperty("providerId")
  private int providerId;

  @JsonProperty("providerId")
  public int getProviderId() {
    return this.providerId;
  }

  @JsonProperty("providerId")
  public void setProviderId(int providerId) {
    this.providerId = providerId;
  }
}

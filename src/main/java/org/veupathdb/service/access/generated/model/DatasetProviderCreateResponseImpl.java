package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "created",
    "providerId"
})
public class DatasetProviderCreateResponseImpl implements DatasetProviderCreateResponse {
  @JsonProperty("created")
  private boolean created;

  @JsonProperty("providerId")
  private int providerId;

  @JsonProperty("created")
  public boolean getCreated() {
    return this.created;
  }

  @JsonProperty("created")
  public void setCreated(boolean created) {
    this.created = created;
  }

  @JsonProperty("providerId")
  public int getProviderId() {
    return this.providerId;
  }

  @JsonProperty("providerId")
  public void setProviderId(int providerId) {
    this.providerId = providerId;
  }
}

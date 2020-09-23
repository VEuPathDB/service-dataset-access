package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "created",
    "endUserId"
})
public class EndUserCreateResponseImpl implements EndUserCreateResponse {
  @JsonProperty("created")
  private boolean created;

  @JsonProperty("endUserId")
  private String endUserId;

  @JsonProperty("created")
  public boolean getCreated() {
    return this.created;
  }

  @JsonProperty("created")
  public void setCreated(boolean created) {
    this.created = created;
  }

  @JsonProperty("endUserId")
  public String getEndUserId() {
    return this.endUserId;
  }

  @JsonProperty("endUserId")
  public void setEndUserId(String endUserId) {
    this.endUserId = endUserId;
  }
}

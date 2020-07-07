package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("endUserId")
public class EndUserCreateResponseImpl implements EndUserCreateResponse {
  @JsonProperty("endUserId")
  private String endUserId;

  @JsonProperty("endUserId")
  public String getEndUserId() {
    return this.endUserId;
  }

  @JsonProperty("endUserId")
  public void setEndUserId(String endUserId) {
    this.endUserId = endUserId;
  }
}

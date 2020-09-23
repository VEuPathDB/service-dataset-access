package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = EndUserCreateResponseImpl.class
)
public interface EndUserCreateResponse {
  @JsonProperty("created")
  boolean getCreated();

  @JsonProperty("created")
  void setCreated(boolean created);

  @JsonProperty("endUserId")
  String getEndUserId();

  @JsonProperty("endUserId")
  void setEndUserId(String endUserId);
}

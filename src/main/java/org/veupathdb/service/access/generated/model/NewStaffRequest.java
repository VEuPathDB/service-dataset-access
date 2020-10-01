package org.veupathdb.service.access.generated.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = NewStaffRequestImpl.class
)
public interface NewStaffRequest {
  @JsonAnyGetter
  Map<String, Object> getAdditionalProperties();

  @JsonAnySetter
  void setAdditionalProperties(String key, Object value);

  @JsonProperty("userId")
  long getUserId();

  @JsonProperty("userId")
  void setUserId(long userId);

  @JsonProperty("isOwner")
  boolean getIsOwner();

  @JsonProperty("isOwner")
  void setIsOwner(boolean isOwner);
}

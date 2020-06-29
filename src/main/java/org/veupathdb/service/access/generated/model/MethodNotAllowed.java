package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonTypeName("bad-method")
@JsonDeserialize(
    as = MethodNotAllowedImpl.class
)
public interface MethodNotAllowed extends Error {
  String _DISCRIMINATOR_TYPE_NAME = "bad-method";

  @JsonProperty("status")
  String getStatus();

  @JsonProperty("message")
  String getMessage();

  @JsonProperty("message")
  void setMessage(String message);
}

package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "status"
)
@JsonSubTypes({
    @JsonSubTypes.Type(org.veupathdb.service.access.generated.model.BadRequest.class),
    @JsonSubTypes.Type(org.veupathdb.service.access.generated.model.Server.class),
    @JsonSubTypes.Type(org.veupathdb.service.access.generated.model.MethodNotAllowed.class),
    @JsonSubTypes.Type(org.veupathdb.service.access.generated.model.Forbidden.class),
    @JsonSubTypes.Type(org.veupathdb.service.access.generated.model.Unauthorized.class),
    @JsonSubTypes.Type(org.veupathdb.service.access.generated.model.UnprocessableEntity.class),
    @JsonSubTypes.Type(org.veupathdb.service.access.generated.model.NotFound.class),
    @JsonSubTypes.Type(org.veupathdb.service.access.generated.model.Error.class)
})
@JsonDeserialize(
    as = ErrorImpl.class
)
public interface Error {
  String _DISCRIMINATOR_TYPE_NAME = "Error";

  @JsonProperty("status")
  String getStatus();

  @JsonProperty("message")
  String getMessage();

  @JsonProperty("message")
  void setMessage(String message);
}

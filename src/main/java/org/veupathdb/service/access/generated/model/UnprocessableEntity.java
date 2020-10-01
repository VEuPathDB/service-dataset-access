package org.veupathdb.service.access.generated.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonTypeName("invalid-input")
@JsonDeserialize(
    as = UnprocessableEntityImpl.class
)
public interface UnprocessableEntity extends Error {
  String _DISCRIMINATOR_TYPE_NAME = "invalid-input";

  @JsonProperty("status")
  String getStatus();

  @JsonProperty("message")
  String getMessage();

  @JsonProperty("message")
  void setMessage(String message);

  @JsonProperty("errors")
  ErrorsType getErrors();

  @JsonProperty("errors")
  void setErrors(ErrorsType errors);

  @JsonDeserialize(
      as = UnprocessableEntityImpl.ErrorsTypeImpl.class
  )
  interface ErrorsType {
    @JsonAnyGetter
    Map<String, Object> getAdditionalProperties();

    @JsonAnySetter
    void setAdditionalProperties(String key, Object value);

    @JsonProperty("general")
    List<String> getGeneral();

    @JsonProperty("general")
    void setGeneral(List<String> general);

    @JsonProperty("byKey")
    ByKeyType getByKey();

    @JsonProperty("byKey")
    void setByKey(ByKeyType byKey);

    @JsonDeserialize(
        as = UnprocessableEntityImpl.ErrorsTypeImpl.ByKeyTypeImpl.class
    )
    interface ByKeyType {
      @JsonAnyGetter
      Map<String, Object> getAdditionalProperties();

      @JsonAnySetter
      void setAdditionalProperties(String key, Object value);
    }
  }
}

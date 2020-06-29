package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = DatasetProviderPatchImpl.class
)
public interface DatasetProviderPatch {
  @JsonProperty("op")
  String getOp();

  @JsonProperty("op")
  void setOp(String op);

  @JsonProperty("path")
  String getPath();

  @JsonProperty("path")
  void setPath(String path);

  @JsonProperty("value")
  boolean getValue();

  @JsonProperty("value")
  void setValue(boolean value);
}

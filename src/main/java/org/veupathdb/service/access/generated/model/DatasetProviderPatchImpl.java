package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "op",
    "path",
    "value"
})
public class DatasetProviderPatchImpl implements DatasetProviderPatch {
  @JsonProperty("op")
  private String op;

  @JsonProperty("path")
  private String path;

  @JsonProperty("value")
  private boolean value;

  @JsonProperty("op")
  public String getOp() {
    return this.op;
  }

  @JsonProperty("op")
  public void setOp(String op) {
    this.op = op;
  }

  @JsonProperty("path")
  public String getPath() {
    return this.path;
  }

  @JsonProperty("path")
  public void setPath(String path) {
    this.path = path;
  }

  @JsonProperty("value")
  public boolean getValue() {
    return this.value;
  }

  @JsonProperty("value")
  public void setValue(boolean value) {
    this.value = value;
  }
}

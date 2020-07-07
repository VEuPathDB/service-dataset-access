package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "op",
    "path",
    "value",
    "from"
})
public class EndUserPatchImpl implements EndUserPatch {
  @JsonProperty("op")
  private EndUserPatch.OpType op;

  @JsonProperty("path")
  private String path;

  @JsonProperty("value")
  private Object value;

  @JsonProperty("from")
  private String from;

  @JsonProperty("op")
  public EndUserPatch.OpType getOp() {
    return this.op;
  }

  @JsonProperty("op")
  public void setOp(EndUserPatch.OpType op) {
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
  public Object getValue() {
    return this.value;
  }

  @JsonProperty("value")
  public void setValue(Object value) {
    this.value = value;
  }

  @JsonProperty("from")
  public String getFrom() {
    return this.from;
  }

  @JsonProperty("from")
  public void setFrom(String from) {
    this.from = from;
  }
}

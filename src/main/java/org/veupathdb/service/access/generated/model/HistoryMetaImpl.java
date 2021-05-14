package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "rows",
    "offset"
})
public class HistoryMetaImpl implements HistoryMeta {
  @JsonProperty("rows")
  private int rows;

  @JsonProperty("offset")
  private int offset;

  @JsonProperty("rows")
  public int getRows() {
    return this.rows;
  }

  @JsonProperty("rows")
  public void setRows(int rows) {
    this.rows = rows;
  }

  @JsonProperty("offset")
  public int getOffset() {
    return this.offset;
  }

  @JsonProperty("offset")
  public void setOffset(int offset) {
    this.offset = offset;
  }
}

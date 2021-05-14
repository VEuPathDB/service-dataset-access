package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cause",
    "row"
})
public class HistoryResultImpl implements HistoryResult {
  @JsonProperty("cause")
  private HistoryCause cause;

  @JsonProperty("row")
  private HistoryRow row;

  @JsonProperty("cause")
  public HistoryCause getCause() {
    return this.cause;
  }

  @JsonProperty("cause")
  public void setCause(HistoryCause cause) {
    this.cause = cause;
  }

  @JsonProperty("row")
  public HistoryRow getRow() {
    return this.row;
  }

  @JsonProperty("row")
  public void setRow(HistoryRow row) {
    this.row = row;
  }
}

package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = HistoryResultImpl.class
)
public interface HistoryResult {
  @JsonProperty("cause")
  HistoryCause getCause();

  @JsonProperty("cause")
  void setCause(HistoryCause cause);

  @JsonProperty("row")
  HistoryRow getRow();

  @JsonProperty("row")
  void setRow(HistoryRow row);
}

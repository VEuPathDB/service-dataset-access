package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = HistoryMetaImpl.class
)
public interface HistoryMeta {
  @JsonProperty("rows")
  int getRows();

  @JsonProperty("rows")
  void setRows(int rows);

  @JsonProperty("offset")
  int getOffset();

  @JsonProperty("offset")
  void setOffset(int offset);
}

package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

@JsonDeserialize(
    as = HistoryResponseImpl.class
)
public interface HistoryResponse {
  @JsonProperty("meta")
  HistoryMeta getMeta();

  @JsonProperty("meta")
  void setMeta(HistoryMeta meta);

  @JsonProperty("results")
  List<HistoryResult> getResults();

  @JsonProperty("results")
  void setResults(List<HistoryResult> results);
}

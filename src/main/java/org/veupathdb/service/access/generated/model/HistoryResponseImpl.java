package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "meta",
    "results"
})
public class HistoryResponseImpl implements HistoryResponse {
  @JsonProperty("meta")
  private HistoryMeta meta;

  @JsonProperty("results")
  private List<HistoryResult> results;

  @JsonProperty("meta")
  public HistoryMeta getMeta() {
    return this.meta;
  }

  @JsonProperty("meta")
  public void setMeta(HistoryMeta meta) {
    this.meta = meta;
  }

  @JsonProperty("results")
  public List<HistoryResult> getResults() {
    return this.results;
  }

  @JsonProperty("results")
  public void setResults(List<HistoryResult> results) {
    this.results = results;
  }
}

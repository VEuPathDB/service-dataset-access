package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "user",
    "action",
    "timestamp"
})
public class HistoryCauseImpl implements HistoryCause {
  @JsonProperty("user")
  private HistoryUser user;

  @JsonProperty("action")
  private HistoryCause.ActionType action;

  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss"
  )
  @JsonProperty("timestamp")
  private Date timestamp;

  @JsonProperty("user")
  public HistoryUser getUser() {
    return this.user;
  }

  @JsonProperty("user")
  public void setUser(HistoryUser user) {
    this.user = user;
  }

  @JsonProperty("action")
  public HistoryCause.ActionType getAction() {
    return this.action;
  }

  @JsonProperty("action")
  public void setAction(HistoryCause.ActionType action) {
    this.action = action;
  }

  @JsonProperty("timestamp")
  public Date getTimestamp() {
    return this.timestamp;
  }

  @JsonProperty("timestamp")
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
}

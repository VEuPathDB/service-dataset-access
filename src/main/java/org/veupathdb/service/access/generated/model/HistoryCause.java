package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

@JsonDeserialize(
    as = HistoryCauseImpl.class
)
public interface HistoryCause {
  @JsonProperty("user")
  HistoryUser getUser();

  @JsonProperty("user")
  void setUser(HistoryUser user);

  @JsonProperty("action")
  ActionType getAction();

  @JsonProperty("action")
  void setAction(ActionType action);

  @JsonProperty("timestamp")
  Date getTimestamp();

  @JsonProperty("timestamp")
  void setTimestamp(Date timestamp);

  enum ActionType {
    @JsonProperty("CREATE")
    CREATE("CREATE"),

    @JsonProperty("UPDATE")
    UPDATE("UPDATE"),

    @JsonProperty("DELETE")
    DELETE("DELETE");

    private String name;

    ActionType(String name) {
      this.name = name;
    }
  }
}

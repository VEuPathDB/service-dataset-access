package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "isManager"
})
public class DatasetPermissionEntryImpl implements DatasetPermissionEntry {
  @JsonProperty("type")
  private DatasetPermissionLevel type;

  @JsonProperty(
      value = "isManager",
      defaultValue = "false"
  )
  private Boolean isManager;

  @JsonProperty("type")
  public DatasetPermissionLevel getType() {
    return this.type;
  }

  @JsonProperty("type")
  public void setType(DatasetPermissionLevel type) {
    this.type = type;
  }

  @JsonProperty(
      value = "isManager",
      defaultValue = "false"
  )
  public Boolean getIsManager() {
    return this.isManager;
  }

  @JsonProperty(
      value = "isManager",
      defaultValue = "false"
  )
  public void setIsManager(Boolean isManager) {
    this.isManager = isManager;
  }
}

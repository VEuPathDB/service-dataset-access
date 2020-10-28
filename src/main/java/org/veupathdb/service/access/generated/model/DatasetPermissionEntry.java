package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = DatasetPermissionEntryImpl.class
)
public interface DatasetPermissionEntry {
  @JsonProperty("type")
  DatasetPermissionLevel getType();

  @JsonProperty("type")
  void setType(DatasetPermissionLevel type);

  @JsonProperty(
      value = "isManager",
      defaultValue = "false"
  )
  Boolean getIsManager();

  @JsonProperty(
      value = "isManager",
      defaultValue = "false"
  )
  void setIsManager(Boolean isManager);
}

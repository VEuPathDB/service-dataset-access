package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = PermissionsGetResponseImpl.class
)
public interface PermissionsGetResponse {
  @JsonProperty(
      value = "isStaff",
      defaultValue = "false"
  )
  Boolean getIsStaff();

  @JsonProperty(
      value = "isStaff",
      defaultValue = "false"
  )
  void setIsStaff(Boolean isStaff);

  @JsonProperty("isOwner")
  Boolean getIsOwner();

  @JsonProperty("isOwner")
  void setIsOwner(Boolean isOwner);

  @JsonProperty("isUnknown")
  String getIsUnknown();

  @JsonProperty("isUnknown")
  void setIsUnknown(String isUnknown);

  @JsonProperty("perDataset")
  PerDatasetType getPerDataset();

  @JsonProperty("perDataset")
  void setPerDataset(PerDatasetType perDataset);

  @JsonDeserialize(
      as = PermissionsGetResponseImpl.PerDatasetTypeImpl.class
  )
  interface PerDatasetType {
  }
}

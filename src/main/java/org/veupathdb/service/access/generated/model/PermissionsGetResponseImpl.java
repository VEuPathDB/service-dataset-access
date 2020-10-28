package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "isStaff",
    "isOwner",
    "isUnknown",
    "perDataset"
})
public class PermissionsGetResponseImpl implements PermissionsGetResponse {
  @JsonProperty(
      value = "isStaff",
      defaultValue = "false"
  )
  private Boolean isStaff;

  @JsonProperty("isOwner")
  private Boolean isOwner;

  @JsonProperty("isUnknown")
  private String isUnknown;

  @JsonProperty("perDataset")
  private PermissionsGetResponse.PerDatasetType perDataset;

  @JsonProperty(
      value = "isStaff",
      defaultValue = "false"
  )
  public Boolean getIsStaff() {
    return this.isStaff;
  }

  @JsonProperty(
      value = "isStaff",
      defaultValue = "false"
  )
  public void setIsStaff(Boolean isStaff) {
    this.isStaff = isStaff;
  }

  @JsonProperty("isOwner")
  public Boolean getIsOwner() {
    return this.isOwner;
  }

  @JsonProperty("isOwner")
  public void setIsOwner(Boolean isOwner) {
    this.isOwner = isOwner;
  }

  @JsonProperty("isUnknown")
  public String getIsUnknown() {
    return this.isUnknown;
  }

  @JsonProperty("isUnknown")
  public void setIsUnknown(String isUnknown) {
    this.isUnknown = isUnknown;
  }

  @JsonProperty("perDataset")
  public PermissionsGetResponse.PerDatasetType getPerDataset() {
    return this.perDataset;
  }

  @JsonProperty("perDataset")
  public void setPerDataset(PermissionsGetResponse.PerDatasetType perDataset) {
    this.perDataset = perDataset;
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonPropertyOrder
  public static class PerDatasetTypeImpl implements PermissionsGetResponse.PerDatasetType {
  }
}

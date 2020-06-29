package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "datasetId",
    "userId",
    "isManager"
})
public class DatasetProviderCreateRequestImpl implements DatasetProviderCreateRequest {
  @JsonProperty("datasetId")
  private String datasetId;

  @JsonProperty("userId")
  private long userId;

  @JsonProperty("isManager")
  private boolean isManager;

  @JsonProperty("datasetId")
  public String getDatasetId() {
    return this.datasetId;
  }

  @JsonProperty("datasetId")
  public void setDatasetId(String datasetId) {
    this.datasetId = datasetId;
  }

  @JsonProperty("userId")
  public long getUserId() {
    return this.userId;
  }

  @JsonProperty("userId")
  public void setUserId(long userId) {
    this.userId = userId;
  }

  @JsonProperty("isManager")
  public boolean getIsManager() {
    return this.isManager;
  }

  @JsonProperty("isManager")
  public void setIsManager(boolean isManager) {
    this.isManager = isManager;
  }
}

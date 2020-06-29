package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "providerId",
    "datasetId",
    "user",
    "isManager"
})
public class DatasetProviderImpl implements DatasetProvider {
  @JsonProperty("providerId")
  private int providerId;

  @JsonProperty("datasetId")
  private String datasetId;

  @JsonProperty("user")
  private UserDetails user;

  @JsonProperty("isManager")
  private boolean isManager;

  @JsonProperty("providerId")
  public int getProviderId() {
    return this.providerId;
  }

  @JsonProperty("providerId")
  public void setProviderId(int providerId) {
    this.providerId = providerId;
  }

  @JsonProperty("datasetId")
  public String getDatasetId() {
    return this.datasetId;
  }

  @JsonProperty("datasetId")
  public void setDatasetId(String datasetId) {
    this.datasetId = datasetId;
  }

  @JsonProperty("user")
  public UserDetails getUser() {
    return this.user;
  }

  @JsonProperty("user")
  public void setUser(UserDetails user) {
    this.user = user;
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

package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "providerId",
    "projectId",
    "datasetId",
    "user",
    "isManager"
})
public class DatasetProviderImpl implements DatasetProvider {
  @JsonProperty("providerId")
  private int providerId;

  @JsonProperty("projectId")
  private int projectId;

  @JsonProperty("datasetId")
  private int datasetId;

  @JsonProperty("user")
  private UserDetails user;

  @JsonProperty("isManager")
  private boolean isManager;

  @JsonIgnore
  private Map<String, Object> additionalProperties = new ExcludingMap();

  @JsonProperty("providerId")
  public int getProviderId() {
    return this.providerId;
  }

  @JsonProperty("providerId")
  public void setProviderId(int providerId) {
    this.providerId = providerId;
  }

  @JsonProperty("projectId")
  public int getProjectId() {
    return this.projectId;
  }

  @JsonProperty("projectId")
  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  @JsonProperty("datasetId")
  public int getDatasetId() {
    return this.datasetId;
  }

  @JsonProperty("datasetId")
  public void setDatasetId(int datasetId) {
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

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperties(String key, Object value) {
    this.additionalProperties.put(key, value);
  }
}

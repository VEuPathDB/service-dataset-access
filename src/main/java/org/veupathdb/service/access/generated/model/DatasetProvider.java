package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Map;

@JsonDeserialize(
    as = DatasetProviderImpl.class
)
public interface DatasetProvider {
  @JsonAnyGetter
  Map<String, Object> getAdditionalProperties();

  @JsonAnySetter
  void setAdditionalProperties(String key, Object value);

  @JsonProperty("providerId")
  int getProviderId();

  @JsonProperty("providerId")
  void setProviderId(int providerId);

  @JsonProperty("projectId")
  int getProjectId();

  @JsonProperty("projectId")
  void setProjectId(int projectId);

  @JsonProperty("datasetId")
  int getDatasetId();

  @JsonProperty("datasetId")
  void setDatasetId(int datasetId);

  @JsonProperty("user")
  UserDetails getUser();

  @JsonProperty("user")
  void setUser(UserDetails user);

  @JsonProperty("isManager")
  boolean getIsManager();

  @JsonProperty("isManager")
  void setIsManager(boolean isManager);
}

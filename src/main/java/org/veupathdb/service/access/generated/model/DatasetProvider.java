package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = DatasetProviderImpl.class
)
public interface DatasetProvider {
  @JsonProperty("providerId")
  int getProviderId();

  @JsonProperty("providerId")
  void setProviderId(int providerId);

  @JsonProperty("datasetId")
  String getDatasetId();

  @JsonProperty("datasetId")
  void setDatasetId(String datasetId);

  @JsonProperty("user")
  UserDetails getUser();

  @JsonProperty("user")
  void setUser(UserDetails user);

  @JsonProperty("isManager")
  boolean getIsManager();

  @JsonProperty("isManager")
  void setIsManager(boolean isManager);
}

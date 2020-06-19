package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Map;

@JsonDeserialize(
    as = StaffImpl.class
)
public interface Staff {
  @JsonAnyGetter
  Map<String, Object> getAdditionalProperties();

  @JsonAnySetter
  void setAdditionalProperties(String key, Object value);

  @JsonProperty("staffId")
  int getStaffId();

  @JsonProperty("staffId")
  void setStaffId(int staffId);

  @JsonProperty("user")
  UserDetails getUser();

  @JsonProperty("user")
  void setUser(UserDetails user);

  @JsonProperty("isOwner")
  boolean getIsOwner();

  @JsonProperty("isOwner")
  void setIsOwner(boolean isOwner);
}

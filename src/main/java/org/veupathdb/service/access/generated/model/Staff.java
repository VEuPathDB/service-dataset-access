package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = StaffImpl.class
)
public interface Staff {
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

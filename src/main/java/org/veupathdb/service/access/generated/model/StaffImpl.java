package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "staffId",
    "user",
    "isOwner"
})
public class StaffImpl implements Staff {
  @JsonProperty("staffId")
  private int staffId;

  @JsonProperty("user")
  private UserDetails user;

  @JsonProperty("isOwner")
  private boolean isOwner;

  @JsonProperty("staffId")
  public int getStaffId() {
    return this.staffId;
  }

  @JsonProperty("staffId")
  public void setStaffId(int staffId) {
    this.staffId = staffId;
  }

  @JsonProperty("user")
  public UserDetails getUser() {
    return this.user;
  }

  @JsonProperty("user")
  public void setUser(UserDetails user) {
    this.user = user;
  }

  @JsonProperty("isOwner")
  public boolean getIsOwner() {
    return this.isOwner;
  }

  @JsonProperty("isOwner")
  public void setIsOwner(boolean isOwner) {
    this.isOwner = isOwner;
  }
}

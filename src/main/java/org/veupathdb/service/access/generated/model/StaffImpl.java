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

  @JsonIgnore
  private Map<String, Object> additionalProperties = new ExcludingMap();

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

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperties(String key, Object value) {
    this.additionalProperties.put(key, value);
  }
}

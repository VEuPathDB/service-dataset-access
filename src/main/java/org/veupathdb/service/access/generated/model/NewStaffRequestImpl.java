package org.veupathdb.service.access.generated.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "userId",
    "isOwner"
})
public class NewStaffRequestImpl implements NewStaffRequest {
  @JsonProperty("userId")
  private long userId;

  @JsonProperty("isOwner")
  private boolean isOwner;

  @JsonIgnore
  private Map<String, Object> additionalProperties = new ExcludingMap();

  @JsonProperty("userId")
  public long getUserId() {
    return this.userId;
  }

  @JsonProperty("userId")
  public void setUserId(long userId) {
    this.userId = userId;
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

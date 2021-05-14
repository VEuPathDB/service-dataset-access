package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = HistoryUserImpl.class
)
public interface HistoryUser {
  @JsonProperty("userID")
  long getUserID();

  @JsonProperty("userID")
  void setUserID(long userID);

  @JsonProperty("firstName")
  String getFirstName();

  @JsonProperty("firstName")
  void setFirstName(String firstName);

  @JsonProperty("lastName")
  String getLastName();

  @JsonProperty("lastName")
  void setLastName(String lastName);

  @JsonProperty("email")
  String getEmail();

  @JsonProperty("email")
  void setEmail(String email);

  @JsonProperty("organization")
  String getOrganization();

  @JsonProperty("organization")
  void setOrganization(String organization);
}

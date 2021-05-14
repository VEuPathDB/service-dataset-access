package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "userID",
    "firstName",
    "lastName",
    "email",
    "organization"
})
public class HistoryUserImpl implements HistoryUser {
  @JsonProperty("userID")
  private long userID;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("email")
  private String email;

  @JsonProperty("organization")
  private String organization;

  @JsonProperty("userID")
  public long getUserID() {
    return this.userID;
  }

  @JsonProperty("userID")
  public void setUserID(long userID) {
    this.userID = userID;
  }

  @JsonProperty("firstName")
  public String getFirstName() {
    return this.firstName;
  }

  @JsonProperty("firstName")
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @JsonProperty("lastName")
  public String getLastName() {
    return this.lastName;
  }

  @JsonProperty("lastName")
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @JsonProperty("email")
  public String getEmail() {
    return this.email;
  }

  @JsonProperty("email")
  public void setEmail(String email) {
    this.email = email;
  }

  @JsonProperty("organization")
  public String getOrganization() {
    return this.organization;
  }

  @JsonProperty("organization")
  public void setOrganization(String organization) {
    this.organization = organization;
  }
}

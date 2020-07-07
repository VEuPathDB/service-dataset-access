package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ApprovalStatus {
  @JsonProperty("approved")
  APPROVED("approved"),

  @JsonProperty("requested")
  REQUESTED("requested"),

  @JsonProperty("denied")
  DENIED("denied");

  private String name;

  ApprovalStatus(String name) {
    this.name = name;
  }
}

package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DatasetPermissionLevel {
  @JsonProperty("provider")
  PROVIDER("provider"),

  @JsonProperty("end-user")
  ENDUSER("end-user");

  private String name;

  DatasetPermissionLevel(String name) {
    this.name = name;
  }
}

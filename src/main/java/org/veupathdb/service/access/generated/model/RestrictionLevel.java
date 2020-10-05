package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RestrictionLevel {
  @JsonProperty("public")
  PUBLIC("public"),

  @JsonProperty("prerelease")
  PRERELEASE("prerelease"),

  @JsonProperty("protected")
  PROTECTED("protected"),

  @JsonProperty("controlled")
  CONTROLLED("controlled"),

  @JsonProperty("private")
  PRIVATE("private");

  private String name;

  RestrictionLevel(String name) {
    this.name = name;
  }
}

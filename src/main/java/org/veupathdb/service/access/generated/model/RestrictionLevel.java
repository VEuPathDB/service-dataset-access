package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RestrictionLevel {
  @JsonProperty("public")
  PUBLIC("public"),

  @JsonProperty("limited")
  LIMITED("limited"),

  @JsonProperty("protected")
  PROTECTED("protected"),

  @JsonProperty("controlled")
  CONTROLLED("controlled"),

  @JsonProperty("admin")
  ADMIN("admin");

  private String name;

  RestrictionLevel(String name) {
    this.name = name;
  }
}

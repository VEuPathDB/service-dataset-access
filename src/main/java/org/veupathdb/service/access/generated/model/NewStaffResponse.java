package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = NewStaffResponseImpl.class
)
public interface NewStaffResponse {
  @JsonProperty("staffId")
  int getStaffId();

  @JsonProperty("staffId")
  void setStaffId(int staffId);
}

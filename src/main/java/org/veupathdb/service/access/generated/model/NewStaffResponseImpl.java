package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("staffId")
public class NewStaffResponseImpl implements NewStaffResponse {
  @JsonProperty("staffId")
  private int staffId;

  @JsonProperty("staffId")
  public int getStaffId() {
    return this.staffId;
  }

  @JsonProperty("staffId")
  public void setStaffId(int staffId) {
    this.staffId = staffId;
  }
}

package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "data",
    "rows",
    "offset",
    "total"
})
public class StaffListImpl implements StaffList {
  @JsonProperty("data")
  private List<Staff> data;

  @JsonProperty("rows")
  private int rows;

  @JsonProperty("offset")
  private int offset;

  @JsonProperty("total")
  private int total;

  @JsonProperty("data")
  public List<Staff> getData() {
    return this.data;
  }

  @JsonProperty("data")
  public void setData(List<Staff> data) {
    this.data = data;
  }

  @JsonProperty("rows")
  public int getRows() {
    return this.rows;
  }

  @JsonProperty("rows")
  public void setRows(int rows) {
    this.rows = rows;
  }

  @JsonProperty("offset")
  public int getOffset() {
    return this.offset;
  }

  @JsonProperty("offset")
  public void setOffset(int offset) {
    this.offset = offset;
  }

  @JsonProperty("total")
  public int getTotal() {
    return this.total;
  }

  @JsonProperty("total")
  public void setTotal(int total) {
    this.total = total;
  }
}

package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

@JsonDeserialize(
    as = StaffListImpl.class
)
public interface StaffList {
  @JsonProperty("data")
  List<Staff> getData();

  @JsonProperty("data")
  void setData(List<Staff> data);

  @JsonProperty("rows")
  int getRows();

  @JsonProperty("rows")
  void setRows(int rows);

  @JsonProperty("offset")
  int getOffset();

  @JsonProperty("offset")
  void setOffset(int offset);

  @JsonProperty("total")
  int getTotal();

  @JsonProperty("total")
  void setTotal(int total);
}

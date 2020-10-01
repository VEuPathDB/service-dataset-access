package org.veupathdb.service.access.generated.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(
    as = DatasetProviderListImpl.class
)
public interface DatasetProviderList {
  @JsonProperty("data")
  List<DatasetProvider> getData();

  @JsonProperty("data")
  void setData(List<DatasetProvider> data);

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

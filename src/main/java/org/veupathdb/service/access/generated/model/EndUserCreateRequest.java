package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

@JsonDeserialize(
    as = EndUserCreateRequestImpl.class
)
public interface EndUserCreateRequest {
  @JsonProperty("userId")
  long getUserId();

  @JsonProperty("userId")
  void setUserId(long userId);

  @JsonProperty("purpose")
  String getPurpose();

  @JsonProperty("purpose")
  void setPurpose(String purpose);

  @JsonProperty("researchQuestion")
  String getResearchQuestion();

  @JsonProperty("researchQuestion")
  void setResearchQuestion(String researchQuestion);

  @JsonProperty("analysisPlan")
  String getAnalysisPlan();

  @JsonProperty("analysisPlan")
  void setAnalysisPlan(String analysisPlan);

  @JsonProperty("disseminationPlan")
  String getDisseminationPlan();

  @JsonProperty("disseminationPlan")
  void setDisseminationPlan(String disseminationPlan);

  @JsonProperty("priorAuth")
  String getPriorAuth();

  @JsonProperty("priorAuth")
  void setPriorAuth(String priorAuth);

  @JsonProperty("datasetId")
  String getDatasetId();

  @JsonProperty("datasetId")
  void setDatasetId(String datasetId);

  @JsonProperty("startDate")
  Date getStartDate();

  @JsonProperty("startDate")
  void setStartDate(Date startDate);

  @JsonProperty("duration")
  int getDuration();

  @JsonProperty("duration")
  void setDuration(int duration);

  @JsonProperty("restrictionLevel")
  RestrictionLevel getRestrictionLevel();

  @JsonProperty("restrictionLevel")
  void setRestrictionLevel(RestrictionLevel restrictionLevel);

  @JsonProperty("approvalStatus")
  ApprovalStatus getApprovalStatus();

  @JsonProperty("approvalStatus")
  void setApprovalStatus(ApprovalStatus approvalStatus);

  @JsonProperty("denialReason")
  String getDenialReason();

  @JsonProperty("denialReason")
  void setDenialReason(String denialReason);
}

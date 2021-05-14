package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

@JsonDeserialize(
    as = HistoryRowImpl.class
)
public interface HistoryRow {
  @JsonProperty("endUserID")
  long getEndUserID();

  @JsonProperty("endUserID")
  void setEndUserID(long endUserID);

  @JsonProperty("user")
  HistoryUser getUser();

  @JsonProperty("user")
  void setUser(HistoryUser user);

  @JsonProperty("datasetPresenterID")
  String getDatasetPresenterID();

  @JsonProperty("datasetPresenterID")
  void setDatasetPresenterID(String datasetPresenterID);

  @JsonProperty("restrictionLevel")
  RestrictionLevelType getRestrictionLevel();

  @JsonProperty("restrictionLevel")
  void setRestrictionLevel(RestrictionLevelType restrictionLevel);

  @JsonProperty("approvalStatus")
  ApprovalStatusType getApprovalStatus();

  @JsonProperty("approvalStatus")
  void setApprovalStatus(ApprovalStatusType approvalStatus);

  @JsonProperty("startDate")
  Date getStartDate();

  @JsonProperty("startDate")
  void setStartDate(Date startDate);

  @JsonProperty("duration")
  long getDuration();

  @JsonProperty("duration")
  void setDuration(long duration);

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

  @JsonProperty("priorAuth")
  String getPriorAuth();

  @JsonProperty("priorAuth")
  void setPriorAuth(String priorAuth);

  @JsonProperty("denialReason")
  String getDenialReason();

  @JsonProperty("denialReason")
  void setDenialReason(String denialReason);

  @JsonProperty("dateDenied")
  Date getDateDenied();

  @JsonProperty("dateDenied")
  void setDateDenied(Date dateDenied);

  @JsonProperty("allowSelfEdits")
  boolean getAllowSelfEdits();

  @JsonProperty("allowSelfEdits")
  void setAllowSelfEdits(boolean allowSelfEdits);

  enum ApprovalStatusType {
    @JsonProperty("APPROVED")
    APPROVED("APPROVED"),

    @JsonProperty("REQUESTED")
    REQUESTED("REQUESTED"),

    @JsonProperty("DENIED")
    DENIED("DENIED");

    private String name;

    ApprovalStatusType(String name) {
      this.name = name;
    }
  }

  enum RestrictionLevelType {
    @JsonProperty("PUBLIC")
    PUBLIC("PUBLIC"),

    @JsonProperty("PRERELEASE")
    PRERELEASE("PRERELEASE"),

    @JsonProperty("PROTECTED")
    PROTECTED("PROTECTED"),

    @JsonProperty("CONTROLLED")
    CONTROLLED("CONTROLLED"),

    @JsonProperty("PRIVATE")
    PRIVATE("PRIVATE");

    private String name;

    RestrictionLevelType(String name) {
      this.name = name;
    }
  }
}

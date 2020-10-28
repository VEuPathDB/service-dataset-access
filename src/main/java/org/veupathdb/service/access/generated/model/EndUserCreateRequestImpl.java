package org.veupathdb.service.access.generated.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "userId",
    "email",
    "purpose",
    "researchQuestion",
    "analysisPlan",
    "disseminationPlan",
    "priorAuth",
    "datasetId",
    "startDate",
    "duration",
    "restrictionLevel",
    "approvalStatus",
    "denialReason"
})
public class EndUserCreateRequestImpl implements EndUserCreateRequest {
  @JsonProperty("userId")
  private Long userId;

  @JsonProperty("email")
  private String email;

  @JsonProperty("purpose")
  private String purpose;

  @JsonProperty("researchQuestion")
  private String researchQuestion;

  @JsonProperty("analysisPlan")
  private String analysisPlan;

  @JsonProperty("disseminationPlan")
  private String disseminationPlan;

  @JsonProperty("priorAuth")
  private String priorAuth;

  @JsonProperty("datasetId")
  private String datasetId;

  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
  )
  @JsonDeserialize(
      using = TimestampDeserializer.class
  )
  @JsonProperty("startDate")
  private Date startDate;

  @JsonProperty("duration")
  private int duration;

  @JsonProperty("restrictionLevel")
  private RestrictionLevel restrictionLevel;

  @JsonProperty("approvalStatus")
  private ApprovalStatus approvalStatus;

  @JsonProperty("denialReason")
  private String denialReason;

  @JsonProperty("userId")
  public Long getUserId() {
    return this.userId;
  }

  @JsonProperty("userId")
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @JsonProperty("email")
  public String getEmail() {
    return this.email;
  }

  @JsonProperty("email")
  public void setEmail(String email) {
    this.email = email;
  }

  @JsonProperty("purpose")
  public String getPurpose() {
    return this.purpose;
  }

  @JsonProperty("purpose")
  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  @JsonProperty("researchQuestion")
  public String getResearchQuestion() {
    return this.researchQuestion;
  }

  @JsonProperty("researchQuestion")
  public void setResearchQuestion(String researchQuestion) {
    this.researchQuestion = researchQuestion;
  }

  @JsonProperty("analysisPlan")
  public String getAnalysisPlan() {
    return this.analysisPlan;
  }

  @JsonProperty("analysisPlan")
  public void setAnalysisPlan(String analysisPlan) {
    this.analysisPlan = analysisPlan;
  }

  @JsonProperty("disseminationPlan")
  public String getDisseminationPlan() {
    return this.disseminationPlan;
  }

  @JsonProperty("disseminationPlan")
  public void setDisseminationPlan(String disseminationPlan) {
    this.disseminationPlan = disseminationPlan;
  }

  @JsonProperty("priorAuth")
  public String getPriorAuth() {
    return this.priorAuth;
  }

  @JsonProperty("priorAuth")
  public void setPriorAuth(String priorAuth) {
    this.priorAuth = priorAuth;
  }

  @JsonProperty("datasetId")
  public String getDatasetId() {
    return this.datasetId;
  }

  @JsonProperty("datasetId")
  public void setDatasetId(String datasetId) {
    this.datasetId = datasetId;
  }

  @JsonProperty("startDate")
  public Date getStartDate() {
    return this.startDate;
  }

  @JsonProperty("startDate")
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  @JsonProperty("duration")
  public int getDuration() {
    return this.duration;
  }

  @JsonProperty("duration")
  public void setDuration(int duration) {
    this.duration = duration;
  }

  @JsonProperty("restrictionLevel")
  public RestrictionLevel getRestrictionLevel() {
    return this.restrictionLevel;
  }

  @JsonProperty("restrictionLevel")
  public void setRestrictionLevel(RestrictionLevel restrictionLevel) {
    this.restrictionLevel = restrictionLevel;
  }

  @JsonProperty("approvalStatus")
  public ApprovalStatus getApprovalStatus() {
    return this.approvalStatus;
  }

  @JsonProperty("approvalStatus")
  public void setApprovalStatus(ApprovalStatus approvalStatus) {
    this.approvalStatus = approvalStatus;
  }

  @JsonProperty("denialReason")
  public String getDenialReason() {
    return this.denialReason;
  }

  @JsonProperty("denialReason")
  public void setDenialReason(String denialReason) {
    this.denialReason = denialReason;
  }
}

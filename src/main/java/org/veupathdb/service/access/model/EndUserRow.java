package org.veupathdb.service.access.model;

import java.time.OffsetDateTime;

public class EndUserRow extends UserRow
{
  private String           datasetId;
  private OffsetDateTime   startDate;
  private int              duration;
  private RestrictionLevel restrictionLevel;
  private String           purpose;
  private String           researchQuestion;
  private String           analysisPlan;
  private String           disseminationPlan;
  private ApprovalStatus   approvalStatus;
  private String           priorAuth;
  private String           denialReason;

  public String getDatasetId() {
    return datasetId;
  }

  public EndUserRow setDatasetId(String datasetId) {
    this.datasetId = datasetId;
    return this;
  }

  public OffsetDateTime getStartDate() {
    return startDate;
  }

  public EndUserRow setStartDate(OffsetDateTime startDate) {
    this.startDate = startDate;
    return this;
  }

  public int getDuration() {
    return duration;
  }

  public EndUserRow setDuration(int duration) {
    this.duration = duration;
    return this;
  }

  public RestrictionLevel getRestrictionLevel() {
    return restrictionLevel;
  }

  public EndUserRow setRestrictionLevel(RestrictionLevel restrictionLevel) {
    this.restrictionLevel = restrictionLevel;
    return this;
  }

  public String getPurpose() {
    return purpose;
  }

  public EndUserRow setPurpose(String purpose) {
    this.purpose = purpose;
    return this;
  }

  public String getResearchQuestion() {
    return researchQuestion;
  }

  public EndUserRow setResearchQuestion(String researchQuestion) {
    this.researchQuestion = researchQuestion;
    return this;
  }

  public String getAnalysisPlan() {
    return analysisPlan;
  }

  public EndUserRow setAnalysisPlan(String analysisPlan) {
    this.analysisPlan = analysisPlan;
    return this;
  }

  public String getDisseminationPlan() {
    return disseminationPlan;
  }

  public EndUserRow setDisseminationPlan(String disseminationPlan) {
    this.disseminationPlan = disseminationPlan;
    return this;
  }

  public ApprovalStatus getApprovalStatus() {
    return approvalStatus;
  }

  public EndUserRow setApprovalStatus(ApprovalStatus approvalStatus) {
    this.approvalStatus = approvalStatus;
    return this;
  }

  public String getPriorAuth() {
    return priorAuth;
  }

  public EndUserRow setPriorAuth(String priorAuth) {
    this.priorAuth = priorAuth;
    return this;
  }

  public String getDenialReason() {
    return denialReason;
  }

  public EndUserRow setDenialReason(String denialReason) {
    this.denialReason = denialReason;
    return this;
  }
}

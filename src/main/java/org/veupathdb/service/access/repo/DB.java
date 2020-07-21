package org.veupathdb.service.access.repo;

public interface DB
{
  interface Schema
  {
    String
      StudyAccess  = "studyaccess",
      Tuning       = "apidbtuning",
      UserAccounts = "useraccounts";
  }

  interface Table
  {
    String
      Accounts         = Schema.UserAccounts + ".accounts",
      ApprovalStatus   = Schema.StudyAccess + ".approval_status",
      Datasets         = Schema.Tuning + ".datasetpresenter",
      EndUsers         = Schema.StudyAccess + ".validdatasetuser",
      Providers        = Schema.StudyAccess + ".providers",
      RestrictionLevel = Schema.StudyAccess + ".restriction_level",
      Staff            = Schema.StudyAccess + ".staff";
  }

  interface Column
  {
    interface EndUser
    {
      String
        AnalysisPlan      = "analysis_plan",
        ApprovalStatus    = "approval_status_id",
        DatasetId         = "dataset_presenter_id",
        DenialReason      = "denial_reason",
        DisseminationPlan = "dissemination_plan",
        Duration          = "duration",
        PriorAuth         = "prior_auth",
        Purpose           = "purpose",
        ResearchQuestion  = "research_question",
        RestrictionLevel  = "restriction_level_id",
        StartDate         = "start_date",
        UserId            = "user_id";
    }

    interface Provider
    {
      String
        ProviderId = "provider_id",
        UserId     = "user_id",
        DatasetId  = "dataset_id",
        IsManager  = "is_manager";
    }

    interface Staff
    {
      String
        StaffId = "staff_id",
        UserId  = "user_id",
        IsOwner = "is_owner";
    }

    interface Accounts
    {
      String
        Email = "email";
    }

    interface Misc
    {
      String
        FirstName    = "first_name",
        LastName     = "last_name",
        Organization = "organization";
    }
  }
}

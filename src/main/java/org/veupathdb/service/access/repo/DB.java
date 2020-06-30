package org.veupathdb.service.access.repo;

public interface DB
{
  interface Schema
  {
    String
      StudyAccess = "studyaccess",
      UserAccounts = "useraccounts";
  }

  interface Table
  {
    String
      Accounts  = Schema.UserAccounts + ".accounts",
      Providers = Schema.StudyAccess + ".providers",
      Staff     = Schema.StudyAccess + ".staff",
      EndUsers  = Schema.StudyAccess + ".validdatasetuser";
  }

  interface Column
  {
    interface EndUser
    {
      String
        AnalysisPlan      = "analysis_plan",
        ApprovalStatus    = "approval_status",
        DatasetId         = "dataset_presenter_id",
        DenialReason      = "denial_reason",
        DisseminationPlan = "dissemination_plan",
        Duration          = "duration",
        PriorAuth         = "prior_auth",
        Purpose           = "purpose",
        ResearchQuestion  = "research_question",
        RestrictionLevel  = "restriction_level",
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

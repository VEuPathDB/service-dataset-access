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
      Accounts          = Schema.UserAccounts + ".accounts",
      ApprovalStatus    = Schema.StudyAccess + ".approval_status",
      Datasets          = Schema.Tuning + ".datasetpresenter",
      EndUsers          = Schema.StudyAccess + ".validdatasetuser",
      Providers         = Schema.StudyAccess + ".providers",
      RestrictionLevel  = Schema.StudyAccess + ".restriction_level",
      Staff             = Schema.StudyAccess + ".staff",
      DatasetProperties = Schema.Tuning + ".datasetproperty";
  }

  interface Column
  {
    interface EndUser
    {
      String
        AllowSelfEdits    = "allow_self_edits",
        AnalysisPlan      = "analysis_plan",
        ApprovalStatus    = "approval_status_id",
        DatasetId         = "dataset_presenter_id",
        DateDenied        = "date_denied",
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

    interface DatasetPresenters
    {
      String
        DatasetId             = "dataset_presenter_id",
        Name                  = "name",
        DatasetNamePattern    = "dataset_name_pattern",
        DisplayName           = "display_name",
        ShortDisplayName      = "short_display_name",
        ShortAttribution      = "short_attribution",
        Summary               = "summary",
        Protocol              = "protocol",
        Description           = "description",
        Usage                 = "usage",
        Caveat                = "caveat",
        Acknowledgement       = "acknowledgement",
        ReleasePolicy         = "release_policy",
        DisplayCategory       = "display_category",
        Type                  = "type",
        Subtype               = "subtype",
        Category              = "category",
        IsSpeciesScope        = "is_species_scope",
        BuildNumberIntroduced = "build_number_introduced",
        DatasetSha1Digest     = "dataset_sha1_digest";
    }

    interface DatasetProperties
    {
      String
        DatasetId = "dataset_presenter_id",
        Property  = "property",
        Value     = "value";
    }

    interface Misc
    {
      // User properties
      String
        FirstName    = "first_name",
        LastName     = "last_name",
        Organization = "organization";

      // dataset properties
      String
        Properties = "properties";
    }
  }
}

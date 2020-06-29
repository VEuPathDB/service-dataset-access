package org.veupathdb.service.access.repo;

public interface DB
{
  interface Schema
  {
    String StudyAccess = "studyaccess";
  }

  interface Table
  {
    String
      Providers = Schema.StudyAccess + ".providers",
      Staff     = Schema.StudyAccess + ".staff",
      EndUsers  = Schema.StudyAccess + ".validdatasetuser";
  }

  interface Sequence {
    String
      Providers = Schema.StudyAccess + ".providers_pkseq",
      Staff     = Schema.StudyAccess + ".staff_pkseq";
  }

  interface Column
  {
    interface Provider
    {
      String
        ProviderId = "provider_id",
        ProjectId  = "project_id",
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

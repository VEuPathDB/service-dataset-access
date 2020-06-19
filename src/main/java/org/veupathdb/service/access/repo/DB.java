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
      Projects  = Schema.StudyAccess + ".projects",
      Providers = Schema.StudyAccess + ".providers",
      Staff     = Schema.StudyAccess + ".staff",
      EndUsers  = Schema.StudyAccess + ".validdatasetuser";
  }

  interface Column
  {
    interface Project
    {
      String
        ProjectId = "project_id",
        Name      = "name";
    }

    interface Provider {
      String
        ProviderId = "provider_id",
        ProjectId  = "project_id",
        UserId     = "user_id",
        DatasetId  = "dataset_id",
        IsManager  = "is_manager";
    }
  }
}

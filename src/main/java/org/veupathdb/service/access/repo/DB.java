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
}

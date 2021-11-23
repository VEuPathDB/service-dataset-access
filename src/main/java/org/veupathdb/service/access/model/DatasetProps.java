package org.veupathdb.service.access.model;

public class DatasetProps {

  public final String id;
  public final String studyId;
  public final DatasetAccessLevel accessLevel;

  public DatasetProps(final String id, final String studyId, final DatasetAccessLevel accessLevel) {
    this.id = id;
    this.studyId = studyId;
    this.accessLevel = accessLevel;
  }

}

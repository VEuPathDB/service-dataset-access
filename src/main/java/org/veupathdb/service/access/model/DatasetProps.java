package org.veupathdb.service.access.model;

public class DatasetProps {

  public final String id;
  public final String studyId;
  public final String sha1hash;
  public final DatasetAccessLevel accessLevel;

  public DatasetProps(final String id, final String studyId, final String sha1hash, final DatasetAccessLevel accessLevel) {
    this.id = id;
    this.studyId = studyId;
    this.sha1hash = sha1hash;
    this.accessLevel = accessLevel;
  }

}

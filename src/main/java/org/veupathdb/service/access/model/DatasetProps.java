package org.veupathdb.service.access.model;

public class DatasetProps {

  public final String datasetId;
  public final String studyId;
  public final String sha1hash;
  public final DatasetAccessLevel accessLevel;
  public final String displayName;
  public final String shortDisplayName;
  public final String description;

  public DatasetProps(
      final String datasetId,
      final String studyId,
      final String sha1hash,
      final DatasetAccessLevel accessLevel,
      final String displayName,
      final String shortDisplayName,
      final String description) {
    this.datasetId = datasetId;
    this.studyId = studyId;
    this.sha1hash = sha1hash;
    this.accessLevel = accessLevel;
    this.displayName = displayName;
    this.shortDisplayName = shortDisplayName;
    this.description = description;
  }

}

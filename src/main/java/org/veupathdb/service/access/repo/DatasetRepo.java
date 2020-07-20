package org.veupathdb.service.access.repo;

public final class DatasetRepo
{
  public interface Select
  {
    /**
     * @param datasetId ID string for the dataset to check.
     *
     * @return whether or not the given datasetId points to an already existent
     * dataset.
     *
     * @throws Exception if a database error occurs while attempting to execute
     * this query.
     */
    static boolean datasetExists(final String datasetId) throws Exception {
      final var sql = SQL.Select.Datasets.Exists;
      try (
        final var cn = Util.getAppDbConnection();
        final var ps = Util.prepareStatement(cn, sql)
      ) {
        ps.setString(1, datasetId);
        try (final var rs = Util.executeQueryLogged(ps, sql)) {
          return rs.next();
        }
      }
    }
  }
}

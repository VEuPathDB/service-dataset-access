package org.veupathdb.service.access.repo;

public final class DatasetRepo
{
  public interface Select
  {
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

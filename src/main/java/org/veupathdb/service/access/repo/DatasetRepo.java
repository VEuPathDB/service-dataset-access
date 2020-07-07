package org.veupathdb.service.access.repo;

public class DatasetRepo
{
  public interface Select
  {
    static boolean datasetExists(final String datasetId) throws Exception {
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = cn.prepareStatement(SQL.Select.Datasets.Exists)
      ) {
        ps.setString(1, datasetId);
        try (final var rs = ps.executeQuery()) {
          return rs.next();
        }
      }
    }
  }
}

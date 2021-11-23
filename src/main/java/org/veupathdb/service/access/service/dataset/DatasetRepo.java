package org.veupathdb.service.access.service.dataset;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.vulpine.lib.query.util.basic.BasicPreparedReadQuery;
import org.veupathdb.service.access.model.Dataset;
import org.veupathdb.service.access.repo.DB;
import org.veupathdb.service.access.repo.SQL;
import org.veupathdb.service.access.service.QueryUtil;
import org.veupathdb.service.access.util.SqlUtil;

public final class DatasetRepo
{
  public static final class Select
  {
    static Select instance;

    public Map<String,DatasetAccessLevel> selectAccessLevelMap() throws Exception {
      final var sql = SQL.Select.Datasets.Access;

      try (
        final var cn = QueryUtil.appDbConnection();
        final var stmt = cn.createStatement();
        final var rs = QueryUtil.executeQueryLogged(stmt, sql);
      ) {
        Map<String,DatasetAccessLevel> accessLevelMap = new HashMap<>();
        while (rs.next()) {
          accessLevelMap.put(
            rs.getString(DB.Column.DatasetPresenters.DatasetId),
            DatasetAccessLevel.valueOf(
              rs.getString(DB.Column.DatasetProperties.Value).toUpperCase()
            )
          );
        }
        return accessLevelMap;
      }
    }

    /**
     * @param datasetId ID string for the dataset to check.
     *
     * @return whether or not the given datasetId points to an already existent
     * dataset.
     *
     * @throws Exception if a database error occurs while attempting to execute
     *                   this query.
     */
    public boolean getDatasetExists(final String datasetId) throws Exception {
      final var sql = SQL.Select.Datasets.Exists;

      try (
        final var cn = QueryUtil.appDbConnection();
        final var ps = QueryUtil.prepareStatement(cn, sql)
      ) {
        ps.setString(1, datasetId);

        try (final var rs = QueryUtil.executeQueryLogged(ps, sql)) {
          return rs.next();
        }
      }
    }

    public Optional<Dataset> selectDataset(final String datasetId) throws Exception {
      return new BasicPreparedReadQuery<>(
        SQL.Select.Datasets.ById,
        QueryUtil.getInstance()::getAppDbConnection,
        SqlUtil.optParser(DatasetUtil.getInstance()::resultSetToDataset),
        SqlUtil.prepareSingleString(datasetId)
      ).execute().getValue();
    }

    public static Select getInstance() {
      if (instance == null)
        instance = new Select();

      return instance;
    }

    public static boolean datasetExists(final String datasetId) throws Exception {
      return getInstance().getDatasetExists(datasetId);
    }

    public static Optional<Dataset> getDataset(final String datasetId) throws Exception {
      return getInstance().selectDataset(datasetId);
    }

    public static Map<String,DatasetAccessLevel> getAccessLevelMap() throws Exception {
      return getInstance().selectAccessLevelMap();
    }
  }
}

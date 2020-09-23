package org.veupathdb.service.access.repo;

import org.veupathdb.service.access.model.DatasetEmails;
import org.veupathdb.service.access.service.QueryUtil;

public final class DatasetRepo
{
  public static final class Select
  {
    static final String
      EMAIL_TO_PROP   = "requestEmailBcc",
      EMAIL_FROM_PROP = "requestEmail";

    static Select instance = new Select();

    Select() {
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

    private static final String
      ERR_DUP  = "Dataset '%s' has duplicate '%s' properties.",
      ERR_UNK  = "Unrecognized property '%s' in results for dataset '%s'.",
      ERR_MISS = "Dataset '%s' is missing property '%s'.";

    public DatasetEmails getDatasetEmails(final String datasetId) throws Exception {
      final var sql = SQL.Select.Datasets.Emails;

      try (
        final var cn = QueryUtil.appDbConnection();
        final var ps = QueryUtil.prepareStatement(cn, sql)
      ) {
        ps.setString(1, datasetId);

        try (final var rs = QueryUtil.executeQueryLogged(ps, sql)) {
          String to = null, from = null;

          while (rs.next()) {
            var prop = rs.getString(1);

            switch (prop) {
              case EMAIL_TO_PROP -> {
                if (to != null)
                  throw new IllegalStateException(String.format(ERR_DUP, datasetId, prop));
                to = rs.getString(2);
              }

              case EMAIL_FROM_PROP -> {
                if (from != null)
                  throw new IllegalStateException(String.format(ERR_DUP, datasetId, prop));
                from = rs.getString(2);
              }

              default -> throw new IllegalStateException(String.format(ERR_UNK, prop, datasetId));

            }
          }

          if (to == null)
            throw new IllegalStateException(String.format(ERR_MISS, datasetId, EMAIL_TO_PROP));

          if (from == null)
            throw new IllegalStateException(String.format(ERR_MISS, datasetId, EMAIL_FROM_PROP));

          return new DatasetEmails(to, from);
        }
      }
    }


    public static Select getInstance() {
      return instance;
    }

    public static boolean datasetExists(final String datasetId) throws Exception {
      return getInstance().getDatasetExists(datasetId);
    }

    public static DatasetEmails datasetEmails(final String datasetId) throws Exception {
      return getInstance().getDatasetEmails(datasetId);
    }
  }
}

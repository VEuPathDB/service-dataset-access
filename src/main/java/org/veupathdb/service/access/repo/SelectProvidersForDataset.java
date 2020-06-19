package org.veupathdb.service.access.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.veupathdb.lib.container.jaxrs.utils.db.DbManager;
import org.veupathdb.service.access.model.ProviderRow;
import org.veupathdb.service.access.repo.DB.Column.Provider;

public class SelectProvidersForDataset
{
  private static final String
    COL_EMAIL      = "email",
    COL_FIRST_NAME = "first_name",
    COL_LAST_NAME  = "last_name";

  public static List < ProviderRow > run(String datasetId) throws Exception {
    try (
      var con = DbManager.accountDatabase().getDataSource().getConnection();
      var ps = con.prepareStatement(SQL.Select.Providers.ByDataset)
    ) {
      ps.setString(1, datasetId);
      try (var rs = ps.executeQuery()) {
        if (!rs.next())
          return Collections.emptyList();

        final var out = new ArrayList < ProviderRow >(10);

        do {
          final var row = new ProviderRow();
          row.setProviderId(rs.getInt(Provider.ProviderId));
          row.setUserId(rs.getInt(Provider.UserId));
          row.setProjectId(rs.getInt(Provider.ProjectId));
          row.setDatasetId(datasetId);
          row.setEmail(rs.getString(COL_EMAIL));
          row.setFirstName(rs.getString(COL_FIRST_NAME));
          row.setLastName(rs.getString(COL_LAST_NAME));
          row.setManager(rs.getBoolean(Provider.IsManager));
          out.add(row);
        } while (rs.next());

        return out;
      }
    }
  }
}

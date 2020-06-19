package org.veupathdb.service.access.repo;

import java.util.Optional;

import org.veupathdb.lib.container.jaxrs.utils.db.DbManager;
import org.veupathdb.service.access.model.ProviderRow;

import static org.veupathdb.service.access.repo.DB.Column.*;

public class SelectProviderById
{
  private static final String
    COL_EMAIL      = "email",
    COL_FIRST_NAME = "first_name",
    COL_LAST_NAME  = "last_name";

  public static Optional < ProviderRow > run(int providerId) throws Exception {
    try (
      var con = DbManager.accountDatabase().getDataSource().getConnection();
      var ps = con.prepareStatement(SQL.Select.Providers.ById)
    ) {
      ps.setInt(1, 1);
      try (var rs = ps.executeQuery()) {
        if (!rs.next())
          return Optional.empty();

        final var out = new ProviderRow();
        out.setProviderId(providerId);
        out.setUserId(rs.getInt(Provider.UserId));
        out.setProjectId(rs.getInt(Provider.ProjectId));
        out.setDatasetId(rs.getString(Provider.DatasetId));
        out.setEmail(rs.getString(COL_EMAIL));
        out.setFirstName(rs.getString(COL_FIRST_NAME));
        out.setLastName(rs.getString(COL_LAST_NAME));
        out.setManager(rs.getBoolean(Provider.IsManager));

        return Optional.of(out);
      }
    }
  }
}

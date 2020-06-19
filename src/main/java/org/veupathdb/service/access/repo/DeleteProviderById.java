package org.veupathdb.service.access.repo;

import org.veupathdb.lib.container.jaxrs.utils.db.DbManager;

public class DeleteProviderById
{
  public static void run(int providerId) throws Exception {
    try (
      var con = DbManager.accountDatabase().getDataSource().getConnection();
      var ps  = con.prepareStatement(SQL.Delete.Providers.ById)
    ) {
      ps.setInt(1, providerId);
      ps.execute();
    }
  }
}

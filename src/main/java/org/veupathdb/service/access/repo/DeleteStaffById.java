package org.veupathdb.service.access.repo;

import org.veupathdb.lib.container.jaxrs.utils.db.DbManager;

public class DeleteStaffById
{
  public static void run(int staffId) throws Exception {
    try (
      var con = DbManager.accountDatabase().getDataSource().getConnection();
      var ps  = con.prepareStatement(SQL.Delete.Staff.ById)
    ) {
      ps.setInt(1, staffId);
      ps.execute();
    }
  }
}

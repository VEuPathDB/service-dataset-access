package org.veupathdb.service.access.repo;

import java.util.ArrayList;
import java.util.List;

import org.veupathdb.lib.container.jaxrs.utils.db.DbManager;
import org.veupathdb.service.access.model.StaffRow;

public class SelectStaffList
{
  public static List < StaffRow > run() throws Exception {
    try (
      var con = DbManager.accountDatabase().getDataSource().getConnection();
      var stm = con.createStatement();
      var rs = stm.executeQuery(SQL.Select.Staff.All)
    ) {
      final var out = new ArrayList < StaffRow >(10);
      while (rs.next()) {
        var tmp = new StaffRow();
        tmp.setStaffId(rs.getInt(DB.Column.Staff.StaffId));
        tmp.setUserId(rs.getInt(DB.Column.Staff.UserId));
        tmp.setOwner(rs.getBoolean(DB.Column.Staff.IsOwner));
        UserQuery.parseUser(tmp, rs);
        out.add(tmp);
      }
      return out;
    }
  }
}

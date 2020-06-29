package org.veupathdb.service.access.repo;

import java.util.Optional;

import org.veupathdb.lib.container.jaxrs.utils.db.DbManager;
import org.veupathdb.service.access.model.StaffRow;

public final class Staff
{
  public static final class Select
  {
    public static Optional < StaffRow > byId(int staffId) throws Exception {
      try (
        var con = DbManager.accountDatabase().getDataSource().getConnection();
        var ps  = con.prepareStatement(SQL.Select.Staff.ById)
      ) {
        ps.setInt(1, staffId);

        try (var rs = ps.executeQuery()) {
          if (!rs.next())
            return Optional.empty();

          var out = new StaffRow();
          out.setStaffId(staffId);
          out.setOwner(rs.getBoolean(DB.Column.Staff.IsOwner));
          out.setUserId(rs.getInt(DB.Column.Staff.UserId));
          UserQuery.parseUser(out, rs);

          return Optional.of(out);
        }
      }
    }

    public static Optional < StaffRow > byUserId(long userId) throws Exception {
      try (
        var con = DbManager.accountDatabase().getDataSource().getConnection();
        var ps  = con.prepareStatement(SQL.Select.Staff.ById)
      ) {
        ps.setLong(1, userId);

        try (var rs = ps.executeQuery()) {
          if (!rs.next())
            return Optional.empty();

          var out = new StaffRow();
          out.setStaffId(rs.getInt(DB.Column.Staff.StaffId));
          out.setOwner(rs.getBoolean(DB.Column.Staff.IsOwner));
          out.setUserId(userId);
          UserQuery.parseUser(out, rs);

          return Optional.of(out);
        }
      }
    }
  }

  public static final class Delete
  {
    public static void byId(int staffId) throws Exception {
      try (
        var con = DbManager.accountDatabase().getDataSource().getConnection();
        var ps  = con.prepareStatement(SQL.Delete.Staff.ById)
      ) {
        ps.setInt(1, staffId);
        ps.execute();
      }
    }
  }

  public static final class Update
  {
    public static void ownerFlagById(StaffRow row) throws Exception {
      try (
        var con = DbManager.accountDatabase().getDataSource().getConnection();
        var ps  = con.prepareStatement(SQL.Update.Staff.ById)
      ) {
        ps.setBoolean(1, row.isOwner());
        ps.setInt(2, row.getStaffId());
        ps.execute();
      }
    }
  }
}

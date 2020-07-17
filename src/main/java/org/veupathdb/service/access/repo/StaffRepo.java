package org.veupathdb.service.access.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.veupathdb.service.access.model.PartialStaffRow;
import org.veupathdb.service.access.model.StaffRow;

public final class StaffRepo
{
  public interface Delete
  {
    static void byId(int staffId) throws Exception {
      final var sql = SQL.Delete.Staff.ById;
      try (
        var con = Util.getAcctDbConnection();
        var ps = con.prepareStatement(sql)
      ) {
        ps.setInt(1, staffId);
        Util.executeLogged(ps, sql);
      }
    }
  }

  public interface Insert {
    static int newStaff(final PartialStaffRow row) throws Exception {
      final var sql = SQL.Insert.Staff;
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = cn.prepareStatement(sql)
      ) {
        ps.setLong(1, row.getUserId());
        ps.setBoolean(1, row.isOwner());

        try (final var rs = Util.executeQueryLogged(ps, sql)) {
          rs.next();
          return rs.getInt(1);
        }
      }
    }
  }

  public interface Select
  {
    static Optional < StaffRow > byId(int staffId) throws Exception {
      final var sql = SQL.Select.Staff.ById;
      try (
        var con = Util.getAcctDbConnection();
        var ps = con.prepareStatement(sql)
      ) {
        ps.setInt(1, staffId);

        try (var rs = Util.executeQueryLogged(ps, sql)) {
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

    static Optional < StaffRow > byUserId(long userId) throws Exception {
      final var sql = SQL.Select.Staff.ByUserId;
      try (
        var con = Util.getAcctDbConnection();
        var ps = con.prepareStatement(sql)
      ) {
        ps.setLong(1, userId);

        try (var rs = Util.executeQueryLogged(ps, sql)) {
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

    static int count() throws Exception {
      try (
        final var cn = Util.getAcctDbConnection();
        final var st = cn.createStatement();
        final var rs = Util.executeQueryLogged(st, SQL.Select.Staff.CountAll)
      ) {
        rs.next();
        return rs.getInt(1);
      }
    }

    static List < StaffRow > list(int limit, int offset) throws Exception {
      final var sql = SQL.Select.Staff.All;
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = cn.prepareStatement(sql)
      ) {
        ps.setInt(1, offset);
        ps.setInt(2, limit);

        try (var rs = Util.executeQueryLogged(ps, sql)) {
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
  } // End::Select

  public interface Update
  {
    static void ownerFlagById(StaffRow row) throws Exception {
      final var sql = SQL.Update.Staff.ById;
      try (
        var con = Util.getAcctDbConnection();
        var ps = con.prepareStatement(sql)
      ) {
        ps.setBoolean(1, row.isOwner());
        ps.setInt(2, row.getStaffId());
        Util.executeLogged(ps, sql);
      }
    }
  }
}

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
      try (
        var con = Util.getAcctDbConnection();
        var ps = con.prepareStatement(SQL.Delete.Staff.ById)
      ) {
        ps.setInt(1, staffId);
        ps.execute();
      }
    }
  }

  public interface Insert {
    static int newStaff(final PartialStaffRow row) throws Exception {
      final var id = Select.nextId();

      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = cn.prepareStatement(SQL.Insert.Staff)
      ) {
        ps.setInt(1, id);
        ps.setLong(2, row.getUserId());
        ps.setBoolean(3, row.isOwner());
        ps.execute();
      }

      return id;
    }
  }

  public interface Select
  {
    static Optional < StaffRow > byId(int staffId) throws Exception {
      try (
        var con = Util.getAcctDbConnection();
        var ps = con.prepareStatement(SQL.Select.Staff.ById)
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

    static Optional < StaffRow > byUserId(long userId) throws Exception {
      try (
        var con = Util.getAcctDbConnection();
        var ps = con.prepareStatement(SQL.Select.Staff.ByUserId)
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

    static int count() throws Exception {
      try (
        final var cn = Util.getAcctDbConnection();
        final var st = cn.createStatement();
        final var rs = st.executeQuery(SQL.Select.Staff.CountAll)
      ) {
        rs.next();
        return rs.getInt(1);
      }
    }

    static List < StaffRow > list(int limit, int offset) throws Exception {
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = cn.prepareStatement(SQL.Select.Staff.All);
      ) {
        ps.setInt(1, offset);
        ps.setInt(2, limit);

        try (var rs = ps.executeQuery()) {
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

    private static int nextId() throws Exception {
      try (
        final var cn = Util.getAcctDbConnection();
        final var st = cn.createStatement();
        final var rs = st.executeQuery(SQL.Select.Staff.NextId)
      ) {
        rs.next();
        return rs.getInt(1);
      }
    }
  } // End::Select

  public interface Update
  {
    static void ownerFlagById(StaffRow row) throws Exception {
      try (
        var con = Util.getAcctDbConnection();
        var ps = con.prepareStatement(SQL.Update.Staff.ById)
      ) {
        ps.setBoolean(1, row.isOwner());
        ps.setInt(2, row.getStaffId());
        ps.execute();
      }
    }
  }
}

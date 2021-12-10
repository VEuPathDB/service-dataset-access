package org.veupathdb.service.access.service.staff;

import java.util.List;
import java.util.Optional;

import io.vulpine.lib.query.util.basic.BasicPreparedListReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedWriteQuery;
import io.vulpine.lib.query.util.basic.BasicStatementReadQuery;
import org.veupathdb.service.access.model.PartialStaffRow;
import org.veupathdb.service.access.model.StaffRow;
import org.veupathdb.service.access.repo.SQL;
import org.veupathdb.service.access.service.QueryUtil;
import org.veupathdb.service.access.util.PsBuilder;
import org.veupathdb.service.access.util.SqlUtil;

public class StaffRepo
{
  public interface Delete
  {
    static void byId(final int staffId) throws Exception {
      new BasicPreparedWriteQuery(
        SQL.Delete.Staff.ById,
        QueryUtil.getInstance()::getAcctDbConnection,
        SqlUtil.prepareSingleInt(staffId)
      ).execute();
    }
  }

  public interface Insert
  {
    static int newStaff(final PartialStaffRow row) throws Exception {
      return new BasicPreparedReadQuery<>(
        SQL.Insert.Staff,
        QueryUtil.getInstance()::getAcctDbConnection,
        SqlUtil.reqParser(SqlUtil::parseSingleInt),
        new PsBuilder()
          .setLong(row.getUserId())
          .setBoolean(row.isOwner())
          ::build
      ).execute().getValue();
    }
  }

  public interface Select
  {
    static Optional<StaffRow> byId(final int staffId) throws Exception {
      return new BasicPreparedReadQuery<>(
        SQL.Select.Staff.ById,
        QueryUtil.getInstance()::getAcctDbConnection,
        SqlUtil.optParser(StaffUtil.getInstance()::resultRowToStaffRow),
        SqlUtil.prepareSingleInt(staffId)
      ).execute().getValue();
    }

    static Optional<StaffRow> byUserId(final long userId) throws Exception {
      return new BasicPreparedReadQuery<>(
        SQL.Select.Staff.ByUserId,
        QueryUtil.getInstance()::getAcctDbConnection,
        SqlUtil.optParser(StaffUtil.getInstance()::resultRowToStaffRow),
        SqlUtil.prepareSingleLong(userId)
      ).execute().getValue();
    }

    static int count() throws Exception {
      return new BasicStatementReadQuery<>(
        SQL.Select.Staff.CountAll,
        QueryUtil.getInstance()::getAcctDbConnection,
        SqlUtil.reqParser(SqlUtil::parseSingleInt)
      ).execute().getValue();
    }

    static List<StaffRow> list(final int limit, final int offset) throws Exception {
      return new BasicPreparedListReadQuery<>(
        SQL.Select.Staff.All,
        QueryUtil.getInstance()::getAcctDbConnection,
        StaffUtil.getInstance()::resultRowToStaffRow,
        new PsBuilder().setInt(offset).setInt(limit)::build
      ).execute().getValue();
    }
  } // End::Select

  public interface Update
  {
    static void ownerFlagById(final StaffRow row) throws Exception {
      new BasicPreparedWriteQuery(
        SQL.Update.Staff.ById,
        QueryUtil.getInstance()::getAcctDbConnection,
        new PsBuilder().setBoolean(row.isOwner()).setLong(row.getStaffId())::build
      ).execute();
    }
  }
}

package org.veupathdb.service.access.service.user;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

import io.vulpine.lib.query.util.basic.BasicPreparedListReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedReadQuery;
import io.vulpine.lib.query.util.basic.BasicPreparedWriteQuery;
import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.access.model.ApprovalStatus;
import org.veupathdb.service.access.model.ApprovalStatusCache;
import org.veupathdb.service.access.model.EndUserRow;
import org.veupathdb.service.access.model.RestrictionLevelCache;
import org.veupathdb.service.access.repo.SQL;
import org.veupathdb.service.access.service.QueryUtil;
import org.veupathdb.service.access.util.PsBuilder;
import org.veupathdb.service.access.util.SqlUtil;

public class EndUserRepo
{
  private static final Logger log = LogProvider.logger(EndUserRepo.class);

  public interface Insert
  {
    static void newEndUser(final EndUserRow row) throws Exception {
      log.trace("EndUserRepo$Insert#newEndUser(EndUserRow)");

      new BasicPreparedWriteQuery(
        SQL.Insert.EndUser,
        QueryUtil.getInstance()::getAcctDbConnection,
        new PsBuilder()
          .setLong(row.getUserId())
          .setString(row.getDatasetId())
          .setShort(RestrictionLevelCache.getInstance()
            .get(row.getRestrictionLevel())
            .orElseThrow())
          .setShort(ApprovalStatusCache.getInstance().get(row.getApprovalStatus()).orElseThrow())
          .setObject(row.getStartDate(), Types.DATE)
          .setLong(row.getDuration())
          .setString(row.getPurpose())
          .setString(row.getResearchQuestion())
          .setString(row.getAnalysisPlan())
          .setString(row.getDisseminationPlan())
          .setString(row.getPriorAuth())
          .setString(row.getDenialReason())
          .setObject(row.getDateDenied(), Types.TIMESTAMP_WITH_TIMEZONE)
          .setBoolean(row.isAllowSelfEdits())
          ::build
      ).execute();
    }
  }

  public interface Select
  {
    static int countByDataset(final String datasetId) throws Exception {
      log.trace("EndUserRepo$Select#countByDataset(String)");

      return new BasicPreparedReadQuery<>(
        SQL.Select.EndUsers.CountByDataset,
        QueryUtil.getInstance()::getAcctDbConnection,
        SqlUtil.reqParser(SqlUtil::parseSingleInt),
        SqlUtil.prepareSingleString(datasetId)
      ).execute().getValue();
    }

    static int countByDatasetFiltered(final String datasetId, final ApprovalStatus status)
    throws Exception {
      log.trace("EndUserRepo$Select#countByDatasetFiltered(String, ApprovalStatus)");

      return new BasicPreparedReadQuery<>(
        SQL.Select.EndUsers.CountByDatasetFiltered,
        QueryUtil.getInstance()::getAcctDbConnection,
        SqlUtil.reqParser(SqlUtil::parseSingleInt),
        new PsBuilder().setString(datasetId).setShort(ApprovalStatusCache.getInstance()
          .get(status)
          .orElseThrow())::build
      ).execute().getValue();
    }

    /**
     * Returns a list of {@link EndUserRow} instances representing the results
     * of a <code>SELECT</code> query searching for at most <code>limit</code>
     * end user records (starting from offset <code>offset+1</code>) matching
     * the given <code>datasetId</code>.
     */
    static List<EndUserRow> list(final String datasetId, final int limit, final int offset)
    throws Exception {
      log.trace("EndUserRepo$Select#list(String, int, int)");

      return new BasicPreparedListReadQuery<>(
        SQL.Select.EndUsers.ByDataset,
        QueryUtil.getInstance()::getAcctDbConnection,
        EndUserUtil::parseEndUserRow,
        new PsBuilder().setString(datasetId).setInt(offset).setInt(limit)::build
      ).execute().getValue();
    }

    /**
     * Returns a list of {@link EndUserRow} instances representing the results
     * of a <code>SELECT</code> query searching for at most <code>limit</code>
     * end user records (starting from offset <code>offset+1</code>) matching
     * the given <code>datasetId</code> and <code>status</code>.
     */
    static List<EndUserRow> filteredList(
      final String datasetId,
      final int limit,
      final int offset,
      final ApprovalStatus status
    ) throws Exception {
      log.trace("EndUserRepo$Select#filteredList(String, int, int, ApprovalStatus)");

      return new BasicPreparedListReadQuery<>(
        SQL.Select.EndUsers.ByDatasetFiltered,
        QueryUtil.getInstance()::getAcctDbConnection,
        EndUserUtil::parseEndUserRow,
        new PsBuilder()
          .setString(datasetId)
          .setShort(ApprovalStatusCache.getInstance().get(status).orElseThrow())
          .setInt(offset)
          .setInt(limit)
          ::build
      ).execute().getValue();
    }

    static Optional<EndUserRow> endUser(final long userId, final String datasetId)
    throws Exception {
      log.trace("EndUserRepo$Select#endUser(long, String)");

      return new BasicPreparedReadQuery<>(
        SQL.Select.EndUsers.ById,
        QueryUtil.getInstance()::getAcctDbConnection,
        SqlUtil.optParser(EndUserUtil::parseEndUserRow),
        new PsBuilder().setLong(userId).setString(datasetId)::build
      ).execute().getValue();
    }
  }

  public interface Update
  {
    static void self(final EndUserRow row) throws Exception {
      log.trace("EndUserRepo$Update#self(EndUserRow)");

      new BasicPreparedWriteQuery(
        SQL.Update.EndUser.SelfUpdate,
        QueryUtil.getInstance()::getAcctDbConnection,
        new PsBuilder()
          .setString(row.getPurpose())
          .setString(row.getResearchQuestion())
          .setString(row.getAnalysisPlan())
          .setString(row.getDisseminationPlan())
          .setString(row.getPriorAuth())
          .setBoolean(row.isAllowSelfEdits())
          .setLong(row.getUserId())
          .setString(row.getDatasetId())
          ::build
      ).execute();
    }

    static void mod(final EndUserRow row) throws Exception {
      log.trace("EndUserRepo$Update#mod(EndUserRow)");

      new BasicPreparedWriteQuery(
        SQL.Update.EndUser.ModUpdate,
        QueryUtil.getInstance()::getAcctDbConnection,
        new PsBuilder()
          .setObject(row.getStartDate(), Types.DATE)
          .setLong(row.getDuration())
          .setString(row.getPurpose())
          .setString(row.getResearchQuestion())
          .setString(row.getAnalysisPlan())
          .setString(row.getDisseminationPlan())
          .setString(row.getPriorAuth())
          .setShort(RestrictionLevelCache.getInstance()
            .get(row.getRestrictionLevel())
            .orElseThrow())
          .setShort(ApprovalStatusCache.getInstance().get(row.getApprovalStatus()).orElseThrow())
          .setString(row.getDenialReason())
          .setObject(row.getDateDenied(), Types.TIMESTAMP_WITH_TIMEZONE)
          .setBoolean(row.isAllowSelfEdits())
          .setLong(row.getUserId())
          .setString(row.getDatasetId())
          ::build
      ).execute();
    }
  }
}

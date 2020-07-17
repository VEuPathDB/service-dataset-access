package org.veupathdb.service.access.repo;

import java.sql.ResultSet;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.veupathdb.service.access.model.ApprovalStatus;
import org.veupathdb.service.access.model.ApprovalStatusCache;
import org.veupathdb.service.access.model.EndUserRow;
import org.veupathdb.service.access.model.RestrictionLevelCache;

public final class EndUserRepo
{
  public interface Insert
  {
    static void newEndUser(final EndUserRow row) throws Exception {
      final var sql = SQL.Insert.EndUser;
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = Util.prepareStatement(cn, sql)
      ) {
        ps.setLong(1, row.getUserId());
        ps.setString(2, row.getDatasetId());
        ps.setShort(3, RestrictionLevelCache.getInstance()
          .get(row.getRestrictionLevel())
          .orElseThrow());
        ps.setShort(4, ApprovalStatusCache.getInstance()
          .get(row.getApprovalStatus())
          .orElseThrow());
        ps.setObject(5, row.getStartDate());
        ps.setInt(6, row.getDuration());
        ps.setString(7, row.getPurpose());
        ps.setString(8, row.getResearchQuestion());
        ps.setString(9, row.getAnalysisPlan());
        ps.setString(10, row.getDisseminationPlan());
        ps.setString(11, row.getPriorAuth());
        ps.setString(12, row.getDenialReason());

        Util.executeLogged(ps, sql);
      }
    }
  }

  public interface Select
  {
    static int countByDataset(final String datasetId) throws Exception {
      final var sql = SQL.Select.EndUsers.CountByDataset;
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = Util.prepareStatement(cn, sql)
      ) {
        ps.setString(1, datasetId);

        try (final var rs = Util.executeQueryLogged(ps, sql)) {
          rs.next();
          return rs.getInt(1);
        }
      }
    }

    static int countByDatasetFiltered(
      final String datasetId,
      final ApprovalStatus status
    ) throws Exception {
      final var sql = SQL.Select.EndUsers.CountByDatasetFiltered;
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = Util.prepareStatement(cn, sql)
      ) {
        ps.setString(1, datasetId);
        ps.setShort(2, ApprovalStatusCache.getInstance()
          .get(status)
          .orElseThrow());

        try (final var rs = Util.executeQueryLogged(ps, sql)) {
          rs.next();
          return rs.getInt(1);
        }
      }
    }

    /**
     * Returns a list of {@link EndUserRow} instances representing the results
     * of a <code>SELECT</code> query searching for at most <code>limit</code>
     * end user records (starting from offset <code>offset+1</code>) matching
     * the given <code>datasetId</code>.
     */
    static List < EndUserRow > list(
      final String datasetId,
      final int limit,
      final int offset
    ) throws Exception {
      final var sql = SQL.Select.EndUsers.ByDataset;
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = cn.prepareStatement(SQL.Select.EndUsers.ByDataset)
      ) {
        ps.setString(1, datasetId);
        ps.setInt(2, offset);
        ps.setInt(3, limit);

        final var out = new ArrayList < EndUserRow >(10);

        try (final var rs = Util.executeQueryLogged(ps, sql)) {
          while (rs.next()) {
            out.add(parseEndUserRow(rs));
          }
        }

        return out;
      }
    }

    /**
     * Returns a list of {@link EndUserRow} instances representing the results
     * of a <code>SELECT</code> query searching for at most <code>limit</code>
     * end user records (starting from offset <code>offset+1</code>) matching
     * the given <code>datasetId</code> and <code>status</code>.
     */
    static List < EndUserRow > filteredList(
      final String datasetId,
      final int limit,
      final int offset,
      final ApprovalStatus status
    ) throws Exception {
      final var sql = SQL.Select.EndUsers.ByDataset;
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = Util.prepareStatement(cn, sql)
      ) {
        ps.setString(1, datasetId);
        ps.setShort(2, ApprovalStatusCache.getInstance()
          .get(status)
          .orElseThrow());
        ps.setInt(3, offset);
        ps.setInt(4, limit);

        final var out = new ArrayList < EndUserRow >(10);

        try (final var rs = Util.executeQueryLogged(ps, sql)) {
          while (rs.next()) {
            out.add(parseEndUserRow(rs));
          }
        }

        return out;
      }
    }

    static Optional < EndUserRow > endUser(
      final long userId,
      final String datasetId
    ) throws Exception {
      final var sql = SQL.Select.EndUsers.ById;
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = Util.prepareStatement(cn, sql)
      ) {
        ps.setLong(1, userId);
        ps.setString(2, datasetId);

        try (final var rs = Util.executeQueryLogged(ps, sql)) {
          if (!rs.next())
            return Optional.empty();

          return Optional.of(parseEndUserRow(rs));
        }
      }
    }

    /**
     * Parses a single {@link ResultSet} row into an instance of
     * {@link EndUserRow}.
     */
    private static EndUserRow parseEndUserRow(final ResultSet rs)
    throws Exception {
      return (EndUserRow) new EndUserRow()
        .setAnalysisPlan(rs.getString(DB.Column.EndUser.AnalysisPlan))
        .setApprovalStatus(ApprovalStatusCache.getInstance()
          .get(rs.getShort(DB.Column.EndUser.ApprovalStatus))
          .orElseThrow())
        .setDatasetId(rs.getString(DB.Column.EndUser.DatasetId))
        .setDisseminationPlan(rs.getString(DB.Column.EndUser.DisseminationPlan))
        .setDuration(rs.getInt(DB.Column.EndUser.Duration))
        .setPriorAuth(rs.getString(DB.Column.EndUser.PriorAuth))
        .setPurpose(rs.getString(DB.Column.EndUser.Purpose))
        .setResearchQuestion(rs.getString(DB.Column.EndUser.ResearchQuestion))
        .setRestrictionLevel(RestrictionLevelCache.getInstance()
          .get(rs.getShort(DB.Column.EndUser.RestrictionLevel))
          .orElseThrow())
        .setStartDate(rs.getObject(
          DB.Column.EndUser.StartDate,
          OffsetDateTime.class
        ))
        .setDenialReason(rs.getString(DB.Column.EndUser.DenialReason))
        .setUserId(rs.getLong(DB.Column.EndUser.UserId))
        .setEmail(rs.getString(DB.Column.Accounts.Email))
        .setOrganization(rs.getString(DB.Column.Misc.Organization))
        .setFirstName(rs.getString(DB.Column.Misc.FirstName))
        .setLastName(rs.getString(DB.Column.Misc.LastName));
    }
  }

  public interface Update
  {
    static void self(EndUserRow row) throws Exception {
      final var sql = SQL.Update.EndUser.SelfUpdate;
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = Util.prepareStatement(cn, sql)
      ) {
        ps.setString(1, row.getPurpose());
        ps.setString(2, row.getResearchQuestion());
        ps.setString(3, row.getAnalysisPlan());
        ps.setString(4, row.getDisseminationPlan());
        ps.setString(5, row.getPriorAuth());
        ps.setLong(6, row.getUserId());
        ps.setString(7, row.getDatasetId());

        Util.executeLogged(ps, sql);
      }
    }

    static void mod(EndUserRow row) throws Exception {
      final var sql = SQL.Update.EndUser.ModUpdate;
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = Util.prepareStatement(cn, sql)
      ) {
        ps.setObject(1, row.getStartDate(), Types.DATE);
        ps.setLong(2, row.getDuration());
        ps.setString(3, row.getPurpose());
        ps.setString(4, row.getResearchQuestion());
        ps.setString(5, row.getAnalysisPlan());
        ps.setString(6, row.getDisseminationPlan());
        ps.setString(7, row.getPriorAuth());
        ps.setShort(8, RestrictionLevelCache.getInstance()
          .get(row.getRestrictionLevel())
          .orElseThrow());
        ps.setShort(9, ApprovalStatusCache.getInstance()
          .get(row.getApprovalStatus())
          .orElseThrow());
        ps.setString(10, row.getDenialReason());
        ps.setLong(11, row.getUserId());
        ps.setString(12, row.getDatasetId());

        Util.executeLogged(ps, sql);
      }
    }
  }
}

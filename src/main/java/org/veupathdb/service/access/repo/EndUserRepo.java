package org.veupathdb.service.access.repo;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.veupathdb.service.access.model.ApprovalStatus;
import org.veupathdb.service.access.model.ApprovalStatusCache;
import org.veupathdb.service.access.model.EndUserRow;
import org.veupathdb.service.access.model.RestrictionLevelCache;

public final class EndUserRepo
{
  public interface Select
  {
    static int countByDataset(final String datasetId) throws Exception {
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = cn.prepareStatement(SQL.Select.EndUsers.CountByDataset)
      ) {
        ps.setString(1, datasetId);

        try (final var rs = ps.executeQuery()) {
          rs.next();
          return rs.getInt(1);
        }
      }
    }

    static int countByDatasetFiltered(
      final String datasetId,
      final ApprovalStatus status
    ) throws Exception {
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = cn.prepareStatement(SQL.Select.EndUsers.CountByDataset)
      ) {
        ps.setString(1, datasetId);
        ps.setShort(2, ApprovalStatusCache.getInstance()
          .get(status)
          .orElseThrow());

        try (final var rs = ps.executeQuery()) {
          rs.next();
          return rs.getInt(1);
        }
      }
    }

    static List < EndUserRow > list(
      final String datasetId,
      final int limit,
      final int offset
    ) throws Exception {
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = cn.prepareStatement(SQL.Select.EndUsers.ByDataset)
      ) {
        ps.setString(1, datasetId);
        ps.setInt(2, offset);
        ps.setInt(3, limit);

        final var out = new ArrayList < EndUserRow >(10);

        try (final var rs = ps.executeQuery()) {
          while (rs.next()) {
            out.add(parseEndUserRow(rs));
          }
        }

        return out;
      }
    }

    static List < EndUserRow > filteredList(
      final String datasetId,
      final int limit,
      final int offset,
      final ApprovalStatus status
    ) throws Exception {
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = cn.prepareStatement(SQL.Select.EndUsers.ByDataset)
      ) {
        ps.setString(1, datasetId);
        ps.setShort(2, ApprovalStatusCache.getInstance()
          .get(status)
          .orElseThrow());
        ps.setInt(3, offset);
        ps.setInt(4, limit);

        final var out = new ArrayList < EndUserRow >(10);

        try (final var rs = ps.executeQuery()) {
          while (rs.next()) {
            out.add(parseEndUserRow(rs));
          }
        }

        return out;
      }
    }

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
          OffsetDateTime.class))
        .setDenialReason(rs.getString(DB.Column.EndUser.DenialReason))
        .setUserId(rs.getLong(DB.Column.EndUser.UserId))
        .setEmail(rs.getString(DB.Column.Accounts.Email))
        .setOrganization(rs.getString(DB.Column.Misc.Organization))
        .setFirstName(rs.getString(DB.Column.Misc.FirstName))
        .setLastName(rs.getString(DB.Column.Misc.LastName));
    }
  }

}

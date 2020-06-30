package org.veupathdb.service.access.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.veupathdb.service.access.model.PartialProviderRow;
import org.veupathdb.service.access.model.ProviderRow;

public final class ProviderRepo
{
  public interface Delete
  {
    static void byId(int providerId) throws Exception {
      try (
        var con = Util.getAcctDbConnection();
        var ps = con.prepareStatement(SQL.Delete.Providers.ById)
      ) {
        ps.setInt(1, providerId);
        ps.execute();
      }
    }
  }

  public interface Insert
  {
    static int newProvider(PartialProviderRow row) throws Exception {
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = cn.prepareStatement(SQL.Insert.Providers)
      ) {
        ps.setLong(1, row.getUserId());
        ps.setBoolean(2, row.isManager());
        ps.setString(3, row.getDatasetId());

        try (final var rs = ps.executeQuery()) {
          rs.next();
          return rs.getInt(1);
        }
      }
    }
  }

  public interface Select
  {
    static List < ProviderRow > byDataset(
      final String datasetId,
      final int limit,
      final int offset
    ) throws Exception {
      try (
        var con = Util.getAcctDbConnection();
        var ps = con.prepareStatement(SQL.Select.Providers.ByDataset)
      ) {
        ps.setString(1, datasetId);
        ps.setInt(2, offset);
        ps.setInt(3, limit);

        try (var rs = ps.executeQuery()) {
          if (!rs.next())
            return Collections.emptyList();

          final var out = new ArrayList < ProviderRow >(10);

          do {
            final var row = new ProviderRow();
            row.setProviderId(rs.getInt(DB.Column.Provider.ProviderId));
            row.setUserId(rs.getInt(DB.Column.Provider.UserId));
            row.setDatasetId(datasetId);
            row.setManager(rs.getBoolean(DB.Column.Provider.IsManager));
            UserQuery.parseUser(row, rs);

            out.add(row);
          } while (rs.next());

          return out;
        }
      }
    }

    static Optional < ProviderRow > byId(int providerId) throws Exception {
      try (
        var con = Util.getAcctDbConnection();
        var ps = con.prepareStatement(SQL.Select.Providers.ById)
      ) {
        ps.setInt(1, providerId);

        try (var rs = ps.executeQuery()) {
          if (!rs.next())
            return Optional.empty();

          final var out = new ProviderRow();
          out.setProviderId(providerId);
          out.setUserId(rs.getInt(DB.Column.Provider.UserId));
          out.setDatasetId(rs.getString(DB.Column.Provider.DatasetId));
          out.setManager(rs.getBoolean(DB.Column.Provider.IsManager));
          UserQuery.parseUser(out, rs);

          return Optional.of(out);
        }
      }
    }

    static Optional < ProviderRow > byUserId(long userId) throws Exception {
      try (
        var con = Util.getAcctDbConnection();
        var ps = con.prepareStatement(SQL.Select.Providers.ById)
      ) {
        ps.setLong(1, userId);

        try (var rs = ps.executeQuery()) {
          if (!rs.next())
            return Optional.empty();

          final var out = new ProviderRow();
          out.setProviderId(rs.getInt(DB.Column.Provider.ProviderId));
          out.setUserId(userId);
          out.setDatasetId(rs.getString(DB.Column.Provider.DatasetId));
          out.setManager(rs.getBoolean(DB.Column.Provider.IsManager));
          UserQuery.parseUser(out, rs);

          return Optional.of(out);
        }
      }
    }

    static int countByDataset(String datasetId) throws Exception {
      try (
        var con = Util.getAcctDbConnection();
        var ps = con.prepareStatement(SQL.Select.Providers.CountByDataset)
      ) {
        ps.setString(1, datasetId);
        try (var rs = ps.executeQuery()) {
          rs.next();
          return rs.getInt(1);
        }
      }
    }
  } // End::Select

  public interface Update
  {
    static void isManagerById(ProviderRow row) throws Exception {
      try (
        var con = Util.getAcctDbConnection();
        var ps = con.prepareStatement(SQL.Update.Providers.ById)
      ) {
        ps.setBoolean(1, row.isManager());
        ps.setInt(2, row.getProviderId());
        ps.execute();
      }
    }
  }
}

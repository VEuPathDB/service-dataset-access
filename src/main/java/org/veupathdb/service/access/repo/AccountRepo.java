package org.veupathdb.service.access.repo;

import org.veupathdb.service.access.service.QueryUtil;

public final class AccountRepo
{
  public interface Select
  {
    static boolean userExists(final long userId) throws Exception {
      final var sql = SQL.Select.Accounts.exists;
      try (
        final var cn = QueryUtil.acctDbConnection();
        final var ps = QueryUtil.prepareStatement(cn, sql)
      ) {
        ps.setLong(1, userId);
        try (final var rs = QueryUtil.executeQueryLogged(ps, sql)) {
          return rs.next();
        }
      }
    }
  }
}

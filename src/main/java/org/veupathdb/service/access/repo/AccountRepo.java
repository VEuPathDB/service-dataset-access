package org.veupathdb.service.access.repo;

public final class AccountRepo
{
  public interface Select
  {
    static boolean userExists(final long userId) throws Exception {
      final var sql = SQL.Select.Accounts.exists;
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = cn.prepareStatement(sql)
      ) {
        ps.setLong(1, userId);
        try (final var rs = Util.executeQueryLogged(ps, sql)) {
          return rs.next();
        }
      }
    }
  }
}

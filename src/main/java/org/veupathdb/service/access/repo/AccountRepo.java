package org.veupathdb.service.access.repo;

public final class AccountRepo
{
  public interface Select
  {
    static boolean userExists(final long userId) throws Exception {
      try (
        final var cn = Util.getAcctDbConnection();
        final var ps = cn.prepareStatement(SQL.Select.Accounts.exists)
      ) {
        ps.setLong(1, userId);
        try (final var rs = ps.executeQuery()) {
          return rs.next();
        }
      }
    }
  }
}

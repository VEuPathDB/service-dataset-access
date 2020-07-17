package org.veupathdb.service.access.repo;

import java.sql.*;
import java.util.function.Supplier;

import io.vulpine.lib.jcfi.CheckedBiFunction;
import io.vulpine.lib.jcfi.CheckedFunction;
import io.vulpine.lib.jcfi.CheckedSupplier;
import org.veupathdb.lib.container.jaxrs.utils.db.DbManager;

final class Util
{
  public static Connection getAcctDbConnection() throws Exception {
    return DbManager.accountDatabase().getDataSource().getConnection();
  }

  public static ResultSet executeQueryLogged(Statement s, String q) throws Exception {
    return exec(s, q, PreparedStatement::executeQuery, Statement::executeQuery);
  }

  public static boolean executeLogged(Statement ps, String sql) throws Exception {
    return exec(ps, sql, PreparedStatement::execute, Statement::execute);
  }

  private static <T> T exec(
    Statement ps,
    String sql,
    CheckedFunction <PreparedStatement, T> fn1,
    CheckedBiFunction <Statement, String, T> fn2
  ) throws Exception {
    try {
      return ps instanceof PreparedStatement
        ? fn1.apply((PreparedStatement) ps)
        : fn2.apply(ps, sql);
    } catch (SQLException e) {
      throw new SQLException("Query failed:\n" + sql, e);
    }
  }
}

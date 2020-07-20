package org.veupathdb.service.access.repo;

import java.sql.*;

import io.vulpine.lib.jcfi.CheckedBiFunction;
import io.vulpine.lib.jcfi.CheckedFunction;
import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.lib.container.jaxrs.utils.db.DbManager;

final class Util
{
  private static final Logger log = LogProvider.logger(Util.class);

  public static Connection getAcctDbConnection() throws Exception {
    log.trace("Util#getAcctDbConnection()");
    return DbManager.accountDatabase().getDataSource().getConnection();
  }

  public static Connection getAppDbConnection() throws Exception {
    log.trace("Util#getAppDbConnection()");
    return DbManager.applicationDatabase().getDataSource().getConnection();
  }

  public static ResultSet executeQueryLogged(Statement s, String q) throws Exception {
    log.trace("Util#executeQueryLogged(s, q)");
    return exec(s, q, PreparedStatement::executeQuery, Statement::executeQuery);
  }

  public static boolean executeLogged(Statement ps, String sql) throws Exception {
    log.trace("Util#executeLogged(ps, sql)");
    return exec(ps, sql, PreparedStatement::execute, Statement::execute);
  }

  public static PreparedStatement prepareStatement(
    final Connection con,
    final String sql
  ) throws Exception {
    log.trace("Util#prepareStatement(con, sql)");
    try {
      return con.prepareStatement(sql);
    } catch (Exception e) {
      throw new Exception("Failed to prepare query:\n" + sql, e);
    }
  }

  public static PreparedStatement prepareStatement(
    final Connection con,
    final String sql,
    final String[] returning
  ) throws Exception {
    log.trace("Util#prepareStatement(con, sql, returning)");
    try {
      return con.prepareStatement(sql, returning);
    } catch (Exception e) {
      throw new Exception("Failed to prepare query:\n" + sql, e);
    }
  }

  private static <T> T exec(
    Statement ps,
    String sql,
    CheckedFunction <PreparedStatement, T> fn1,
    CheckedBiFunction <Statement, String, T> fn2
  ) throws Exception {
    log.trace("Util#exec(ps, sql, fn1, fn2)");
    try {
      return ps instanceof PreparedStatement
        ? fn1.apply((PreparedStatement) ps)
        : fn2.apply(ps, sql);
    } catch (Exception e) {
      throw new Exception("Query failed:\n" + sql, e);
    }
  }
}

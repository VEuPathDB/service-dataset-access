package org.veupathdb.service.access.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

import org.veupathdb.lib.container.jaxrs.utils.db.DbManager;

final class Util
{
  public static Connection getAcctDbConnection() throws Exception {
    return DbManager.accountDatabase().getDataSource().getConnection();
  }
}

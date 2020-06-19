package org.veupathdb.service.access.repo;

import java.util.function.Supplier;

import io.vulpine.lib.sql.load.SqlLoader;
import org.veupathdb.service.access.repo.DB.Table;

public interface SQL
{
  SqlLoader loader = new SqlLoader();

  enum Method
  {
    SELECT, UPDATE, INSERT, DELETE
  }

  interface Delete
  {
    interface Providers
    {
      String ById = delete(Table.Providers, "by-id");
    }

    interface Staff
    {
      String ById = delete(Table.Staff, "by-id");
    }
  }

  interface Select
  {
    interface Projects
    {
      String
        All = select(Table.Projects, "all");
    }

    interface Providers
    {
      String
        ByDataset = select(Table.Providers, "by-dataset"),
        ById      = select(Table.Providers, "by-id");
    }

    interface Staff
    {
      String
        All  = select(Table.Staff, "all"),
        ById = select(Table.Staff, "by-id");
    }
  }

  interface Update
  {
    interface Providers
    {
      String ById = update(Table.Providers, "by-id");
    }

    interface Staff
    {
      String ById = update(Table.Staff, "by-id");
    }
  }

  private static String select(String path, String query) {
    return loader.select(path + "." + query)
      .orElseThrow(makeErr(Method.SELECT, path, query));
  }

  private static String delete(String path, String query) {
    return loader.delete(path + "." + query)
      .orElseThrow(makeErr(Method.DELETE, path, query));
  }

  private static String update(String path, String query) {
    return loader.udpate(path + "." + query)
      .orElseThrow(makeErr(Method.UPDATE, path, query));
  }

  private static Supplier < RuntimeException > makeErr(
    Method method,
    String path,
    String query
  ) {
    return () -> new RuntimeException("Failed to load " + method.name() +
      " query sql/" + path.replace(".", "/") + "/" + query);
  }
}

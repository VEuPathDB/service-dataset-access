package org.veupathdb.service.access.repo;

import java.util.function.Supplier;

import io.vulpine.lib.sql.load.SqlLoader;
import org.veupathdb.service.access.repo.DB.Table;

interface SQL
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

  interface Insert
  {
    String
      EndUser   = insert(Table.EndUsers, "insert"),
      Providers = insert(Table.Providers, "insert"),
      Staff     = insert(Table.Staff, "insert");
  }

  interface Select
  {
    interface Accounts
    {
      String exists = select(Table.Accounts, "exists-by-id");
    }

    interface EndUsers
    {
      String
        ById                   = select(Table.EndUsers, "by-id"),
        CountByDataset         = select(Table.EndUsers, "count-by-dataset"),
        CountByDatasetFiltered = select(Table.EndUsers, "count-by-dataset-filtered"),
        ByDataset              = select(Table.EndUsers, "by-dataset"),
        ByDatasetFiltered      = select(Table.EndUsers, "by-dataset-filtered");
    }

    interface Providers
    {
      String
        ByDataset      = select(Table.Providers, "by-dataset"),
        ById           = select(Table.Providers, "by-id"),
        CountByDataset = select(Table.Providers, "count-by-dataset");
    }

    interface Staff
    {
      String
        All      = select(Table.Staff, "all"),
        ById     = select(Table.Staff, "by-id"),
        ByUserId = select(Table.Staff, "by-user-id"),
        CountAll = select(Table.Staff, "count");
    }

    interface Datasets
    {
      String Exists = select(Table.Datasets, "exists");
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

    interface EndUser
    {
      String
        ModUpdate  = update(Table.EndUsers, "mod-update"),
        SelfUpdate = update(Table.EndUsers, "self-update");
    }
  }

  private static String delete(String path, String query) {
    return loader.delete(path + "." + query)
      .orElseThrow(makeErr(Method.DELETE, path, query));
  }

  private static String insert(String path, String query) {
    return loader.insert(path + "." + query)
      .orElseThrow(makeErr(Method.INSERT, path, query));
  }

  private static String select(String path, String query) {
    return loader.select(path + "." + query)
      .orElseThrow(makeErr(Method.SELECT, path, query));
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

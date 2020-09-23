package org.veupathdb.service.access.service.provider;

import java.sql.ResultSet;

import org.veupathdb.service.access.model.ProviderRow;
import org.veupathdb.service.access.repo.DB;
import org.veupathdb.service.access.service.user.UserUtil;

public class ProviderUtil
{
  private static ProviderUtil instance = new ProviderUtil();

  public ProviderRow resultToProviderRow(final ResultSet rs) throws Exception {
    var row = new ProviderRow();

    row.setProviderId(rs.getInt(DB.Column.Provider.ProviderId));
    row.setUserId(rs.getInt(DB.Column.Provider.UserId));
    row.setDatasetId(rs.getString(DB.Column.Provider.DatasetId));
    row.setManager(rs.getBoolean(DB.Column.Provider.IsManager));

    UserUtil.fillUser(rs, row);

    return row;
  }

  public static ProviderUtil getInstance() {
    return instance;
  }

  public static ProviderRow rs2Row(final ResultSet rs) throws Exception {
    return getInstance().resultToProviderRow(rs);
  }
}

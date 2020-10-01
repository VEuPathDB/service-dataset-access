package org.veupathdb.service.access.service.provider;

import java.sql.ResultSet;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.access.generated.model.DatasetProvider;
import org.veupathdb.service.access.generated.model.DatasetProviderImpl;
import org.veupathdb.service.access.generated.model.UserDetailsImpl;
import org.veupathdb.service.access.model.ProviderRow;
import org.veupathdb.service.access.repo.DB;
import org.veupathdb.service.access.service.user.UserUtil;

public class ProviderUtil
{
  private static ProviderUtil instance = new ProviderUtil();

  private final Logger log;

  public ProviderUtil() {
    log = LogProvider.logger(getClass());
  }

  public ProviderRow resultToProviderRow(final ResultSet rs) throws Exception {
    var row = new ProviderRow();

    row.setProviderId(rs.getInt(DB.Column.Provider.ProviderId));
    row.setUserId(rs.getInt(DB.Column.Provider.UserId));
    row.setDatasetId(rs.getString(DB.Column.Provider.DatasetId));
    row.setManager(rs.getBoolean(DB.Column.Provider.IsManager));

    UserUtil.fillUser(rs, row);

    return row;
  }

  public DatasetProvider internalToExternal(final ProviderRow row) {
    log.trace("ProviderService#internalToExternal(ProviderRow)");

    var user = new UserDetailsImpl();
    user.setUserId(row.getUserId());
    user.setFirstName(row.getFirstName());
    user.setLastName(row.getLastName());
    user.setOrganization(row.getOrganization());

    var out = new DatasetProviderImpl();
    out.setDatasetId(row.getDatasetId());
    out.setIsManager(row.isManager());
    out.setProviderId(row.getProviderId());
    out.setUser(user);

    return out;
  }


  public static ProviderUtil getInstance() {
    return instance;
  }

  public static ProviderRow rs2Row(final ResultSet rs) throws Exception {
    return getInstance().resultToProviderRow(rs);
  }
}

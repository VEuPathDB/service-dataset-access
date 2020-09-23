package org.veupathdb.service.access.service.user;

import java.sql.ResultSet;

import org.veupathdb.service.access.model.UserRow;
import org.veupathdb.service.access.repo.DB;

public class UserUtil
{
  private static UserUtil instance = new UserUtil();

  public void fillUserRow(final ResultSet rs, final UserRow row) throws Exception {
    row.setUserId(rs.getLong(DB.Column.EndUser.UserId));
    row.setEmail(rs.getString(DB.Column.Accounts.Email));
    row.setFirstName(rs.getString(DB.Column.Misc.FirstName));
    row.setLastName(rs.getString(DB.Column.Misc.LastName));
    row.setOrganization(rs.getString(DB.Column.Misc.Organization));
  }

  public static UserUtil getInstance() {
    return instance;
  }

  public static void fillUser(final ResultSet rs, final UserRow row) throws Exception {
    getInstance().fillUserRow(rs, row);
  }
}

package org.veupathdb.service.access.service;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Request;

import org.gusdb.fgputil.accountdb.UserProfile;
import org.veupathdb.lib.container.jaxrs.providers.UserProvider;
import org.veupathdb.service.access.model.StaffRow;
import org.veupathdb.service.access.repo.Staff;

public class StaffService
{
  public static boolean userIsOwner(Request req) {
    return userIsOwner(UserProvider.lookupUser(req)
      .orElseThrow(InternalServerErrorException::new));
  }

  public static boolean userIsOwner(UserProfile user) {
    try {
      return Staff.Select.byUserId(user.getUserId())
        .filter(StaffRow::isOwner)
        .isPresent();
    } catch (WebApplicationException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }
}

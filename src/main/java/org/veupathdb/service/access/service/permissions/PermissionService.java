package org.veupathdb.service.access.service.permissions;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Request;

import org.veupathdb.lib.container.jaxrs.model.User;
import org.veupathdb.service.access.controller.Util;
import org.veupathdb.service.access.generated.model.PermissionsGetResponse;
import org.veupathdb.service.access.generated.model.PermissionsGetResponseImpl;
import org.veupathdb.service.access.service.provider.ProviderRepo;
import org.veupathdb.service.access.service.staff.StaffRepo;
import org.veupathdb.service.access.service.user.EndUserRepo;

public class PermissionService
{
  private static PermissionService instance;

  public PermissionsGetResponse getUserPermissions(Request request) {
    return getUserPermissions(Util.requireUser(request));
  }

  public PermissionsGetResponse getUserPermissions(User user) {
    var out = new PermissionsGetResponseImpl();

    try {
      StaffRepo.Select.byUserId(user.getUserID())
        .ifPresent(s -> {
          if (s.isOwner())
            out.setIsOwner(true);
          else
            out.setIsStaff(true);
        });

      var tmp = new PermissionMap();

      ProviderRepo.Select.datasets(user.getUserID())
        .forEach((k, v) -> tmp.put(k, PermissionUtil.getInstance().bool2entry(v)));

      EndUserRepo.Select.datasets(user.getUserID())
        .forEach(s -> tmp.putIfAbsent(s, PermissionUtil.getInstance().string2entry(s)));

      if (!tmp.isEmpty())
        out.setPerDataset(tmp);

      return out;
    } catch (WebApplicationException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static PermissionService getInstance() {
    if (instance == null)
      instance = new PermissionService();

    return instance;
  }
}

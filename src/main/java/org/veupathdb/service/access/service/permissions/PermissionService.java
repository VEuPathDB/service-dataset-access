package org.veupathdb.service.access.service.permissions;

import java.util.HashMap;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Request;

import org.gusdb.fgputil.accountdb.UserProfile;
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

  public PermissionsGetResponse getUserPermissions(UserProfile user) {
    var out = new PermissionsGetResponseImpl();

    try {
      StaffRepo.Select.byUserId(user.getUserId())
        .ifPresent(s -> {
          if (s.isOwner())
            out.setIsOwner(true);
          else
            out.setIsStaff(true);
        });

      var tmp = new PermissionMap();

      ProviderRepo.Select.datasets(user.getUserId())
        .forEach((k, v) -> tmp.put(k, PermissionUtil.getInstance().bool2entry(v)));

      EndUserRepo.Select.datasets(user.getUserId())
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

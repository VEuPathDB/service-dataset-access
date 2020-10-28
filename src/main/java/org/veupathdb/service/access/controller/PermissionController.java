package org.veupathdb.service.access.controller;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.veupathdb.lib.container.jaxrs.server.annotations.Authenticated;
import org.veupathdb.service.access.generated.resources.Permissions;
import org.veupathdb.service.access.service.permissions.PermissionService;

@Authenticated
public class PermissionController implements Permissions
{
  private final Request request;

  public PermissionController(@Context Request request) {
    this.request = request;
  }

  @Override
  public GetPermissionsResponse getPermissions() {
    return GetPermissionsResponse.respond200WithApplicationJson(
      PermissionService.getInstance().getUserPermissions(request)
    );
  }
}

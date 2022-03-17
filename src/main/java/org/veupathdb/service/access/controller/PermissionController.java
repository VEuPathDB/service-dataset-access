package org.veupathdb.service.access.controller;

import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.ContainerRequest;
import org.veupathdb.lib.container.jaxrs.server.annotations.Authenticated;
import org.veupathdb.service.access.generated.resources.Permissions;
import org.veupathdb.service.access.service.permissions.PermissionService;

@Authenticated(allowGuests = true)
public class PermissionController implements Permissions
{
  @Context
  private ContainerRequest _request;

  @Override
  public GetPermissionsResponse getPermissions() {
    return GetPermissionsResponse.respond200WithApplicationJson(
      PermissionService.getInstance().getUserPermissions(_request)
    );
  }
}

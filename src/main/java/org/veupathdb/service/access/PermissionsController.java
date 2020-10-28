package org.veupathdb.service.access;

import javax.ws.rs.core.Request;

import org.veupathdb.service.access.generated.resources.Permissions;

public class PermissionsController implements Permissions
{
  private final Request request;

  public PermissionsController(Request request) {
    this.request = request;
  }

  @Override
  public GetPermissionsResponse getPermissions() {
    return null;
  }
}

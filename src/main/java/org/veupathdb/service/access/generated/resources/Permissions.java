package org.veupathdb.service.access.generated.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.veupathdb.service.access.generated.model.PermissionsGetResponse;
import org.veupathdb.service.access.generated.model.Server;
import org.veupathdb.service.access.generated.model.Unauthorized;
import org.veupathdb.service.access.generated.support.ResponseDelegate;

@Path("/permissions")
public interface Permissions {
  @GET
  @Produces("application/json")
  GetPermissionsResponse getPermissions();

  class GetPermissionsResponse extends ResponseDelegate {
    private GetPermissionsResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetPermissionsResponse(Response response) {
      super(response);
    }

    public static GetPermissionsResponse respond200WithApplicationJson(
        PermissionsGetResponse entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetPermissionsResponse(responseBuilder.build(), entity);
    }

    public static GetPermissionsResponse respond401WithApplicationJson(Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetPermissionsResponse(responseBuilder.build(), entity);
    }

    public static GetPermissionsResponse respond500WithApplicationJson(Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetPermissionsResponse(responseBuilder.build(), entity);
    }
  }
}

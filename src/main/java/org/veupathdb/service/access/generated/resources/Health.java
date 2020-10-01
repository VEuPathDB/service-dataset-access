package org.veupathdb.service.access.generated.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.veupathdb.service.access.generated.model.HealthResponse;
import org.veupathdb.service.access.generated.model.Server;
import org.veupathdb.service.access.generated.support.ResponseDelegate;

@Path("/health")
public interface Health {
  @GET
  @Produces("application/json")
  GetHealthResponse getHealth();

  class GetHealthResponse extends ResponseDelegate {
    private GetHealthResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetHealthResponse(Response response) {
      super(response);
    }

    public static GetHealthResponse respond200WithApplicationJson(HealthResponse entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetHealthResponse(responseBuilder.build(), entity);
    }

    public static GetHealthResponse respond500WithApplicationJson(Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetHealthResponse(responseBuilder.build(), entity);
    }
  }
}

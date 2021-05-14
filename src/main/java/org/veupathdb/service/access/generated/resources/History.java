package org.veupathdb.service.access.generated.resources;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.veupathdb.service.access.generated.model.HistoryResponse;
import org.veupathdb.service.access.generated.model.Server;
import org.veupathdb.service.access.generated.model.Unauthorized;
import org.veupathdb.service.access.generated.support.ResponseDelegate;

@Path("/history")
public interface History {
  @GET
  @Produces("application/json")
  GetHistoryResponse getHistory(@QueryParam("limit") @DefaultValue("100") int limit,
      @QueryParam("offset") @DefaultValue("0") int offset);

  class GetHistoryResponse extends ResponseDelegate {
    private GetHistoryResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetHistoryResponse(Response response) {
      super(response);
    }

    public static GetHistoryResponse respond200WithApplicationJson(HistoryResponse entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetHistoryResponse(responseBuilder.build(), entity);
    }

    public static GetHistoryResponse respond401WithApplicationJson(Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetHistoryResponse(responseBuilder.build(), entity);
    }

    public static GetHistoryResponse respond500WithApplicationJson(Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetHistoryResponse(responseBuilder.build(), entity);
    }
  }
}

package org.veupathdb.service.access.generated.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.veupathdb.service.access.generated.model.BadRequest;
import org.veupathdb.service.access.generated.model.Forbidden;
import org.veupathdb.service.access.generated.model.NotFound;
import org.veupathdb.service.access.generated.model.Server;
import org.veupathdb.service.access.generated.model.Unauthorized;
import org.veupathdb.service.access.generated.model.UnprocessableEntity;
import org.veupathdb.service.access.generated.support.ResponseDelegate;

@Path("/dataset-end-users")
public interface DatasetEndUsers {
  @GET
  @Produces("application/json")
  GetDatasetEndUsersResponse getDatasetEndUsers(@QueryParam("datasetId") int datasetId);

  @POST
  @Produces("application/json")
  PostDatasetEndUsersResponse postDatasetEndUsers();

  @GET
  @Path("/{end-user-id}")
  @Produces("application/json")
  GetDatasetEndUsersByEndUserIdResponse getDatasetEndUsersByEndUserId(
      @PathParam("end-user-id") int endUserId);

  @PUT
  @Path("/{end-user-id}")
  @Produces("application/json")
  PutDatasetEndUsersByEndUserIdResponse putDatasetEndUsersByEndUserId(
      @PathParam("end-user-id") int endUserId);

  class GetDatasetEndUsersResponse extends ResponseDelegate {
    private GetDatasetEndUsersResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetDatasetEndUsersResponse(Response response) {
      super(response);
    }

    public static GetDatasetEndUsersResponse respond200() {
      Response.ResponseBuilder responseBuilder = Response.status(200);
      return new GetDatasetEndUsersResponse(responseBuilder.build());
    }

    public static GetDatasetEndUsersResponse respond401WithApplicationJson(Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetDatasetEndUsersResponse(responseBuilder.build(), entity);
    }

    public static GetDatasetEndUsersResponse respond403WithApplicationJson(Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetDatasetEndUsersResponse(responseBuilder.build(), entity);
    }

    public static GetDatasetEndUsersResponse respond500WithApplicationJson(Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetDatasetEndUsersResponse(responseBuilder.build(), entity);
    }
  }

  class PostDatasetEndUsersResponse extends ResponseDelegate {
    private PostDatasetEndUsersResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PostDatasetEndUsersResponse(Response response) {
      super(response);
    }

    public static PostDatasetEndUsersResponse respond200() {
      Response.ResponseBuilder responseBuilder = Response.status(200);
      return new PostDatasetEndUsersResponse(responseBuilder.build());
    }

    public static PostDatasetEndUsersResponse respond400WithApplicationJson(BadRequest entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostDatasetEndUsersResponse(responseBuilder.build(), entity);
    }

    public static PostDatasetEndUsersResponse respond401WithApplicationJson(Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostDatasetEndUsersResponse(responseBuilder.build(), entity);
    }

    public static PostDatasetEndUsersResponse respond403WithApplicationJson(Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostDatasetEndUsersResponse(responseBuilder.build(), entity);
    }

    public static PostDatasetEndUsersResponse respond422WithApplicationJson(
        UnprocessableEntity entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostDatasetEndUsersResponse(responseBuilder.build(), entity);
    }

    public static PostDatasetEndUsersResponse respond500WithApplicationJson(Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostDatasetEndUsersResponse(responseBuilder.build(), entity);
    }
  }

  class GetDatasetEndUsersByEndUserIdResponse extends ResponseDelegate {
    private GetDatasetEndUsersByEndUserIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetDatasetEndUsersByEndUserIdResponse(Response response) {
      super(response);
    }

    public static GetDatasetEndUsersByEndUserIdResponse respond200() {
      Response.ResponseBuilder responseBuilder = Response.status(200);
      return new GetDatasetEndUsersByEndUserIdResponse(responseBuilder.build());
    }

    public static GetDatasetEndUsersByEndUserIdResponse respond401WithApplicationJson(
        Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }

    public static GetDatasetEndUsersByEndUserIdResponse respond403WithApplicationJson(
        Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }

    public static GetDatasetEndUsersByEndUserIdResponse respond404WithApplicationJson(
        NotFound entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }

    public static GetDatasetEndUsersByEndUserIdResponse respond500WithApplicationJson(
        Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }
  }

  class PutDatasetEndUsersByEndUserIdResponse extends ResponseDelegate {
    private PutDatasetEndUsersByEndUserIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PutDatasetEndUsersByEndUserIdResponse(Response response) {
      super(response);
    }

    public static PutDatasetEndUsersByEndUserIdResponse respond204() {
      Response.ResponseBuilder responseBuilder = Response.status(204);
      return new PutDatasetEndUsersByEndUserIdResponse(responseBuilder.build());
    }

    public static PutDatasetEndUsersByEndUserIdResponse respond400WithApplicationJson(
        BadRequest entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }

    public static PutDatasetEndUsersByEndUserIdResponse respond401WithApplicationJson(
        Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }

    public static PutDatasetEndUsersByEndUserIdResponse respond403WithApplicationJson(
        Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }

    public static PutDatasetEndUsersByEndUserIdResponse respond404WithApplicationJson(
        NotFound entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }

    public static PutDatasetEndUsersByEndUserIdResponse respond422WithApplicationJson(
        UnprocessableEntity entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }

    public static PutDatasetEndUsersByEndUserIdResponse respond500WithApplicationJson(
        Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }
  }
}

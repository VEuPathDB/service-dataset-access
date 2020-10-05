package org.veupathdb.service.access.generated.resources;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.veupathdb.service.access.generated.model.ApprovalStatus;
import org.veupathdb.service.access.generated.model.BadRequest;
import org.veupathdb.service.access.generated.model.EndUser;
import org.veupathdb.service.access.generated.model.EndUserCreateRequest;
import org.veupathdb.service.access.generated.model.EndUserCreateResponse;
import org.veupathdb.service.access.generated.model.EndUserList;
import org.veupathdb.service.access.generated.model.EndUserPatch;
import org.veupathdb.service.access.generated.model.Forbidden;
import org.veupathdb.service.access.generated.model.NotFound;
import org.veupathdb.service.access.generated.model.Server;
import org.veupathdb.service.access.generated.model.Unauthorized;
import org.veupathdb.service.access.generated.model.UnprocessableEntity;
import org.veupathdb.service.access.generated.support.PATCH;
import org.veupathdb.service.access.generated.support.ResponseDelegate;

@Path("/dataset-end-users")
public interface DatasetEndUsers {
  @GET
  @Produces("application/json")
  GetDatasetEndUsersResponse getDatasetEndUsers(@QueryParam("datasetId") String datasetId,
      @QueryParam("limit") @DefaultValue("100") int limit,
      @QueryParam("offset") @DefaultValue("0") int offset,
      @QueryParam("approval") ApprovalStatus approval);

  @POST
  @Produces("application/json")
  @Consumes("application/json")
  PostDatasetEndUsersResponse postDatasetEndUsers(EndUserCreateRequest entity);

  @GET
  @Path("/{end-user-id}")
  @Produces("application/json")
  GetDatasetEndUsersByEndUserIdResponse getDatasetEndUsersByEndUserId(
      @PathParam("end-user-id") String endUserId);

  @PATCH
  @Path("/{end-user-id}")
  @Produces("application/json")
  @Consumes("application/json")
  PatchDatasetEndUsersByEndUserIdResponse patchDatasetEndUsersByEndUserId(
      @PathParam("end-user-id") String endUserId, List<EndUserPatch> entity);

  class GetDatasetEndUsersResponse extends ResponseDelegate {
    private GetDatasetEndUsersResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetDatasetEndUsersResponse(Response response) {
      super(response);
    }

    public static GetDatasetEndUsersResponse respond200WithApplicationJson(EndUserList entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetDatasetEndUsersResponse(responseBuilder.build(), entity);
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

    public static PostDatasetEndUsersResponse respond200WithApplicationJson(
        EndUserCreateResponse entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostDatasetEndUsersResponse(responseBuilder.build(), entity);
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

    public static GetDatasetEndUsersByEndUserIdResponse respond200WithApplicationJson(
        EndUser entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
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

  class PatchDatasetEndUsersByEndUserIdResponse extends ResponseDelegate {
    private PatchDatasetEndUsersByEndUserIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PatchDatasetEndUsersByEndUserIdResponse(Response response) {
      super(response);
    }

    public static PatchDatasetEndUsersByEndUserIdResponse respond204() {
      Response.ResponseBuilder responseBuilder = Response.status(204);
      return new PatchDatasetEndUsersByEndUserIdResponse(responseBuilder.build());
    }

    public static PatchDatasetEndUsersByEndUserIdResponse respond400WithApplicationJson(
        BadRequest entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }

    public static PatchDatasetEndUsersByEndUserIdResponse respond401WithApplicationJson(
        Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }

    public static PatchDatasetEndUsersByEndUserIdResponse respond403WithApplicationJson(
        Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }

    public static PatchDatasetEndUsersByEndUserIdResponse respond404WithApplicationJson(
        NotFound entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }

    public static PatchDatasetEndUsersByEndUserIdResponse respond422WithApplicationJson(
        UnprocessableEntity entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }

    public static PatchDatasetEndUsersByEndUserIdResponse respond500WithApplicationJson(
        Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }
  }
}

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

    public static GetDatasetEndUsersResponse respond200WithApplicationJson(EndUserList entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetDatasetEndUsersResponse(responseBuilder.build(), entity);
    }
  }

  class PostDatasetEndUsersResponse extends ResponseDelegate {
    private PostDatasetEndUsersResponse(Response response, Object entity) {
      super(response, entity);
    }

    public static PostDatasetEndUsersResponse respond200WithApplicationJson(
        EndUserCreateResponse entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostDatasetEndUsersResponse(responseBuilder.build(), entity);
    }
  }

  class GetDatasetEndUsersByEndUserIdResponse extends ResponseDelegate {
    private GetDatasetEndUsersByEndUserIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    public static GetDatasetEndUsersByEndUserIdResponse respond200WithApplicationJson(
        EndUser entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetDatasetEndUsersByEndUserIdResponse(responseBuilder.build(), entity);
    }
  }

  class PatchDatasetEndUsersByEndUserIdResponse extends ResponseDelegate {
    private PatchDatasetEndUsersByEndUserIdResponse(Response response) {
      super(response);
    }

    public static PatchDatasetEndUsersByEndUserIdResponse respond204() {
      Response.ResponseBuilder responseBuilder = Response.status(204);
      return new PatchDatasetEndUsersByEndUserIdResponse(responseBuilder.build());
    }
  }
}

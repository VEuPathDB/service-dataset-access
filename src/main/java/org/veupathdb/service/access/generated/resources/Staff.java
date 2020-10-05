package org.veupathdb.service.access.generated.resources;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.veupathdb.service.access.generated.model.BadRequest;
import org.veupathdb.service.access.generated.model.Forbidden;
import org.veupathdb.service.access.generated.model.NewStaffRequest;
import org.veupathdb.service.access.generated.model.NewStaffResponse;
import org.veupathdb.service.access.generated.model.NotFound;
import org.veupathdb.service.access.generated.model.Server;
import org.veupathdb.service.access.generated.model.StaffList;
import org.veupathdb.service.access.generated.model.StaffPatch;
import org.veupathdb.service.access.generated.model.Unauthorized;
import org.veupathdb.service.access.generated.model.UnprocessableEntity;
import org.veupathdb.service.access.generated.support.PATCH;
import org.veupathdb.service.access.generated.support.ResponseDelegate;

@Path("/staff")
public interface Staff {
  @GET
  @Produces("application/json")
  GetStaffResponse getStaff(@QueryParam("limit") @DefaultValue("1000") int limit,
      @QueryParam("offset") @DefaultValue("0") int offset);

  @POST
  @Produces("application/json")
  @Consumes("application/json")
  PostStaffResponse postStaff(NewStaffRequest entity);

  @PATCH
  @Path("/{staff-id}")
  @Produces("application/json")
  @Consumes("application/json")
  PatchStaffByStaffIdResponse patchStaffByStaffId(@PathParam("staff-id") int staffId,
      List<StaffPatch> entity);

  @DELETE
  @Path("/{staff-id}")
  @Produces("application/json")
  DeleteStaffByStaffIdResponse deleteStaffByStaffId(@PathParam("staff-id") int staffId);

  class PostStaffResponse extends ResponseDelegate {
    private PostStaffResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PostStaffResponse(Response response) {
      super(response);
    }

    public static PostStaffResponse respond200WithApplicationJson(NewStaffResponse entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostStaffResponse(responseBuilder.build(), entity);
    }

    public static PostStaffResponse respond400WithApplicationJson(BadRequest entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostStaffResponse(responseBuilder.build(), entity);
    }

    public static PostStaffResponse respond401WithApplicationJson(Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostStaffResponse(responseBuilder.build(), entity);
    }

    public static PostStaffResponse respond403WithApplicationJson(Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostStaffResponse(responseBuilder.build(), entity);
    }

    public static PostStaffResponse respond422WithApplicationJson(UnprocessableEntity entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostStaffResponse(responseBuilder.build(), entity);
    }

    public static PostStaffResponse respond500WithApplicationJson(Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostStaffResponse(responseBuilder.build(), entity);
    }
  }

  class GetStaffResponse extends ResponseDelegate {
    private GetStaffResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetStaffResponse(Response response) {
      super(response);
    }

    public static GetStaffResponse respond200WithApplicationJson(StaffList entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetStaffResponse(responseBuilder.build(), entity);
    }

    public static GetStaffResponse respond401WithApplicationJson(Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetStaffResponse(responseBuilder.build(), entity);
    }

    public static GetStaffResponse respond403WithApplicationJson(Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetStaffResponse(responseBuilder.build(), entity);
    }

    public static GetStaffResponse respond500WithApplicationJson(Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetStaffResponse(responseBuilder.build(), entity);
    }
  }

  class DeleteStaffByStaffIdResponse extends ResponseDelegate {
    private DeleteStaffByStaffIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private DeleteStaffByStaffIdResponse(Response response) {
      super(response);
    }

    public static DeleteStaffByStaffIdResponse respond204() {
      Response.ResponseBuilder responseBuilder = Response.status(204);
      return new DeleteStaffByStaffIdResponse(responseBuilder.build());
    }

    public static DeleteStaffByStaffIdResponse respond401WithApplicationJson(Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new DeleteStaffByStaffIdResponse(responseBuilder.build(), entity);
    }

    public static DeleteStaffByStaffIdResponse respond403WithApplicationJson(Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new DeleteStaffByStaffIdResponse(responseBuilder.build(), entity);
    }

    public static DeleteStaffByStaffIdResponse respond404WithApplicationJson(NotFound entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new DeleteStaffByStaffIdResponse(responseBuilder.build(), entity);
    }

    public static DeleteStaffByStaffIdResponse respond500WithApplicationJson(Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new DeleteStaffByStaffIdResponse(responseBuilder.build(), entity);
    }
  }

  class PatchStaffByStaffIdResponse extends ResponseDelegate {
    private PatchStaffByStaffIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PatchStaffByStaffIdResponse(Response response) {
      super(response);
    }

    public static PatchStaffByStaffIdResponse respond204() {
      Response.ResponseBuilder responseBuilder = Response.status(204);
      return new PatchStaffByStaffIdResponse(responseBuilder.build());
    }

    public static PatchStaffByStaffIdResponse respond400WithApplicationJson(BadRequest entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchStaffByStaffIdResponse(responseBuilder.build(), entity);
    }

    public static PatchStaffByStaffIdResponse respond401WithApplicationJson(Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchStaffByStaffIdResponse(responseBuilder.build(), entity);
    }

    public static PatchStaffByStaffIdResponse respond403WithApplicationJson(Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchStaffByStaffIdResponse(responseBuilder.build(), entity);
    }

    public static PatchStaffByStaffIdResponse respond404WithApplicationJson(NotFound entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchStaffByStaffIdResponse(responseBuilder.build(), entity);
    }

    public static PatchStaffByStaffIdResponse respond422WithApplicationJson(
        UnprocessableEntity entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchStaffByStaffIdResponse(responseBuilder.build(), entity);
    }

    public static PatchStaffByStaffIdResponse respond500WithApplicationJson(Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchStaffByStaffIdResponse(responseBuilder.build(), entity);
    }
  }
}

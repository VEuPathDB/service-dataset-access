package org.veupathdb.service.access.generated.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import org.veupathdb.service.access.generated.model.BadRequest;
import org.veupathdb.service.access.generated.model.Forbidden;
import org.veupathdb.service.access.generated.model.NotFound;
import org.veupathdb.service.access.generated.model.Server;
import org.veupathdb.service.access.generated.model.StaffList;
import org.veupathdb.service.access.generated.model.Unauthorized;
import org.veupathdb.service.access.generated.model.UnprocessableEntity;
import org.veupathdb.service.access.generated.support.ResponseDelegate;

@Path("/staff")
public interface Staff {
  @GET
  @Produces("application/json")
  GetStaffResponse getStaff();

  @POST
  @Produces("application/json")
  PostStaffResponse postStaff();

  @PUT
  @Path("/{staff-id}")
  @Produces("application/json")
  PutStaffByStaffIdResponse putStaffByStaffId(@PathParam("staff-id") int staffId);

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

    public static PostStaffResponse respond200() {
      Response.ResponseBuilder responseBuilder = Response.status(200);
      return new PostStaffResponse(responseBuilder.build());
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
      GenericEntity<StaffList> wrappedEntity = new GenericEntity<StaffList>(entity){};
      responseBuilder.entity(wrappedEntity);
      return new GetStaffResponse(responseBuilder.build(), wrappedEntity);
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

  class PutStaffByStaffIdResponse extends ResponseDelegate {
    private PutStaffByStaffIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PutStaffByStaffIdResponse(Response response) {
      super(response);
    }

    public static PutStaffByStaffIdResponse respond204() {
      Response.ResponseBuilder responseBuilder = Response.status(204);
      return new PutStaffByStaffIdResponse(responseBuilder.build());
    }

    public static PutStaffByStaffIdResponse respond400WithApplicationJson(BadRequest entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutStaffByStaffIdResponse(responseBuilder.build(), entity);
    }

    public static PutStaffByStaffIdResponse respond401WithApplicationJson(Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutStaffByStaffIdResponse(responseBuilder.build(), entity);
    }

    public static PutStaffByStaffIdResponse respond403WithApplicationJson(Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutStaffByStaffIdResponse(responseBuilder.build(), entity);
    }

    public static PutStaffByStaffIdResponse respond404WithApplicationJson(NotFound entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutStaffByStaffIdResponse(responseBuilder.build(), entity);
    }

    public static PutStaffByStaffIdResponse respond422WithApplicationJson(
        UnprocessableEntity entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutStaffByStaffIdResponse(responseBuilder.build(), entity);
    }

    public static PutStaffByStaffIdResponse respond500WithApplicationJson(Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutStaffByStaffIdResponse(responseBuilder.build(), entity);
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
}

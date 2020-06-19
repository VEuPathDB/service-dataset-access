package org.veupathdb.service.access.generated.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import org.veupathdb.service.access.generated.model.BadRequest;
import org.veupathdb.service.access.generated.model.DatasetProviderList;
import org.veupathdb.service.access.generated.model.Forbidden;
import org.veupathdb.service.access.generated.model.NotFound;
import org.veupathdb.service.access.generated.model.Server;
import org.veupathdb.service.access.generated.model.Unauthorized;
import org.veupathdb.service.access.generated.model.UnprocessableEntity;
import org.veupathdb.service.access.generated.support.ResponseDelegate;

@Path("/dataset-providers")
public interface DatasetProviders {
  @GET
  @Produces("application/json")
  GetListResponse getList(@QueryParam("datasetId") String datasetId);

  @POST
  @Produces("application/json")
  PostResponse post();

  @PUT
  @Path("/{provider-id}")
  @Produces("application/json")
  PutByIdResponse putById(
      @PathParam("provider-id") int providerId);

  @DELETE
  @Path("/{provider-id}")
  @Produces("application/json")
  DeleteByIdResponse deleteById(
      @PathParam("provider-id") int providerId);

  class PostResponse extends ResponseDelegate {
    private PostResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PostResponse(Response response) {
      super(response);
    }

    public static PostResponse respond200() {
      Response.ResponseBuilder responseBuilder = Response.status(200);
      return new PostResponse(responseBuilder.build());
    }

    public static PostResponse respond400WithApplicationJson(BadRequest entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostResponse(responseBuilder.build(), entity);
    }

    public static PostResponse respond401WithApplicationJson(Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostResponse(responseBuilder.build(), entity);
    }

    public static PostResponse respond403WithApplicationJson(Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostResponse(responseBuilder.build(), entity);
    }

    public static PostResponse respond422WithApplicationJson(
        UnprocessableEntity entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostResponse(responseBuilder.build(), entity);
    }

    public static PostResponse respond500WithApplicationJson(Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostResponse(responseBuilder.build(), entity);
    }
  }

  class GetListResponse extends ResponseDelegate {
    private GetListResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetListResponse(Response response) {
      super(response);
    }

    public static GetListResponse respond200WithApplicationJson(
        DatasetProviderList entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      GenericEntity<DatasetProviderList> wrappedEntity = new GenericEntity<DatasetProviderList>(entity){};
      responseBuilder.entity(wrappedEntity);
      return new GetListResponse(responseBuilder.build(), wrappedEntity);
    }

    public static GetListResponse respond401WithApplicationJson(Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetListResponse(responseBuilder.build(), entity);
    }

    public static GetListResponse respond403WithApplicationJson(Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetListResponse(responseBuilder.build(), entity);
    }

    public static GetListResponse respond500WithApplicationJson(Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetListResponse(responseBuilder.build(), entity);
    }
  }

  class DeleteByIdResponse extends ResponseDelegate {
    private DeleteByIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private DeleteByIdResponse(Response response) {
      super(response);
    }

    public static DeleteByIdResponse respond204() {
      Response.ResponseBuilder responseBuilder = Response.status(204);
      return new DeleteByIdResponse(responseBuilder.build());
    }

    public static DeleteByIdResponse respond401WithApplicationJson(
        Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new DeleteByIdResponse(responseBuilder.build(), entity);
    }

    public static DeleteByIdResponse respond403WithApplicationJson(
        Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new DeleteByIdResponse(responseBuilder.build(), entity);
    }

    public static DeleteByIdResponse respond404WithApplicationJson(
        NotFound entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new DeleteByIdResponse(responseBuilder.build(), entity);
    }

    public static DeleteByIdResponse respond500WithApplicationJson(
        Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new DeleteByIdResponse(responseBuilder.build(), entity);
    }
  }

  class PutByIdResponse extends ResponseDelegate {
    private PutByIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PutByIdResponse(Response response) {
      super(response);
    }

    public static PutByIdResponse respond204() {
      Response.ResponseBuilder responseBuilder = Response.status(204);
      return new PutByIdResponse(responseBuilder.build());
    }

    public static PutByIdResponse respond400WithApplicationJson(
        BadRequest entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutByIdResponse(responseBuilder.build(), entity);
    }

    public static PutByIdResponse respond401WithApplicationJson(
        Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutByIdResponse(responseBuilder.build(), entity);
    }

    public static PutByIdResponse respond403WithApplicationJson(
        Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutByIdResponse(responseBuilder.build(), entity);
    }

    public static PutByIdResponse respond404WithApplicationJson(
        NotFound entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutByIdResponse(responseBuilder.build(), entity);
    }

    public static PutByIdResponse respond422WithApplicationJson(
        UnprocessableEntity entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutByIdResponse(responseBuilder.build(), entity);
    }

    public static PutByIdResponse respond500WithApplicationJson(
        Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PutByIdResponse(responseBuilder.build(), entity);
    }
  }
}

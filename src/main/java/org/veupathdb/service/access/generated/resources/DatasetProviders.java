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
import org.veupathdb.service.access.generated.model.DatasetProviderCreateRequest;
import org.veupathdb.service.access.generated.model.DatasetProviderCreateResponse;
import org.veupathdb.service.access.generated.model.DatasetProviderList;
import org.veupathdb.service.access.generated.model.DatasetProviderPatch;
import org.veupathdb.service.access.generated.model.Forbidden;
import org.veupathdb.service.access.generated.model.NotFound;
import org.veupathdb.service.access.generated.model.Server;
import org.veupathdb.service.access.generated.model.Unauthorized;
import org.veupathdb.service.access.generated.model.UnprocessableEntity;
import org.veupathdb.service.access.generated.support.PATCH;
import org.veupathdb.service.access.generated.support.ResponseDelegate;

@Path("/dataset-providers")
public interface DatasetProviders {
  @GET
  @Produces("application/json")
  GetDatasetProvidersResponse getDatasetProviders(@QueryParam("datasetId") String datasetId,
      @QueryParam("limit") @DefaultValue("100") int limit,
      @QueryParam("offset") @DefaultValue("0") int offset);

  @POST
  @Produces("application/json")
  @Consumes("application/json")
  PostDatasetProvidersResponse postDatasetProviders(DatasetProviderCreateRequest entity);

  @PATCH
  @Path("/{provider-id}")
  @Produces("application/json")
  @Consumes("application/json")
  PatchDatasetProvidersByProviderIdResponse patchDatasetProvidersByProviderId(
      @PathParam("provider-id") int providerId, List<DatasetProviderPatch> entity);

  @DELETE
  @Path("/{provider-id}")
  @Produces("application/json")
  DeleteDatasetProvidersByProviderIdResponse deleteDatasetProvidersByProviderId(
      @PathParam("provider-id") int providerId);

  class PostDatasetProvidersResponse extends ResponseDelegate {
    private PostDatasetProvidersResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PostDatasetProvidersResponse(Response response) {
      super(response);
    }

    public static PostDatasetProvidersResponse respond200WithApplicationJson(
        DatasetProviderCreateResponse entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostDatasetProvidersResponse(responseBuilder.build(), entity);
    }

    public static PostDatasetProvidersResponse respond400WithApplicationJson(BadRequest entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostDatasetProvidersResponse(responseBuilder.build(), entity);
    }

    public static PostDatasetProvidersResponse respond401WithApplicationJson(Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostDatasetProvidersResponse(responseBuilder.build(), entity);
    }

    public static PostDatasetProvidersResponse respond403WithApplicationJson(Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostDatasetProvidersResponse(responseBuilder.build(), entity);
    }

    public static PostDatasetProvidersResponse respond422WithApplicationJson(
        UnprocessableEntity entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostDatasetProvidersResponse(responseBuilder.build(), entity);
    }

    public static PostDatasetProvidersResponse respond500WithApplicationJson(Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PostDatasetProvidersResponse(responseBuilder.build(), entity);
    }
  }

  class GetDatasetProvidersResponse extends ResponseDelegate {
    private GetDatasetProvidersResponse(Response response, Object entity) {
      super(response, entity);
    }

    private GetDatasetProvidersResponse(Response response) {
      super(response);
    }

    public static GetDatasetProvidersResponse respond200WithApplicationJson(
        DatasetProviderList entity) {
      Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetDatasetProvidersResponse(responseBuilder.build(), entity);
    }

    public static GetDatasetProvidersResponse respond401WithApplicationJson(Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetDatasetProvidersResponse(responseBuilder.build(), entity);
    }

    public static GetDatasetProvidersResponse respond403WithApplicationJson(Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetDatasetProvidersResponse(responseBuilder.build(), entity);
    }

    public static GetDatasetProvidersResponse respond500WithApplicationJson(Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new GetDatasetProvidersResponse(responseBuilder.build(), entity);
    }
  }

  class DeleteDatasetProvidersByProviderIdResponse extends ResponseDelegate {
    private DeleteDatasetProvidersByProviderIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private DeleteDatasetProvidersByProviderIdResponse(Response response) {
      super(response);
    }

    public static DeleteDatasetProvidersByProviderIdResponse respond204() {
      Response.ResponseBuilder responseBuilder = Response.status(204);
      return new DeleteDatasetProvidersByProviderIdResponse(responseBuilder.build());
    }

    public static DeleteDatasetProvidersByProviderIdResponse respond401WithApplicationJson(
        Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new DeleteDatasetProvidersByProviderIdResponse(responseBuilder.build(), entity);
    }

    public static DeleteDatasetProvidersByProviderIdResponse respond403WithApplicationJson(
        Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new DeleteDatasetProvidersByProviderIdResponse(responseBuilder.build(), entity);
    }

    public static DeleteDatasetProvidersByProviderIdResponse respond404WithApplicationJson(
        NotFound entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new DeleteDatasetProvidersByProviderIdResponse(responseBuilder.build(), entity);
    }

    public static DeleteDatasetProvidersByProviderIdResponse respond500WithApplicationJson(
        Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new DeleteDatasetProvidersByProviderIdResponse(responseBuilder.build(), entity);
    }
  }

  class PatchDatasetProvidersByProviderIdResponse extends ResponseDelegate {
    private PatchDatasetProvidersByProviderIdResponse(Response response, Object entity) {
      super(response, entity);
    }

    private PatchDatasetProvidersByProviderIdResponse(Response response) {
      super(response);
    }

    public static PatchDatasetProvidersByProviderIdResponse respond204() {
      Response.ResponseBuilder responseBuilder = Response.status(204);
      return new PatchDatasetProvidersByProviderIdResponse(responseBuilder.build());
    }

    public static PatchDatasetProvidersByProviderIdResponse respond400WithApplicationJson(
        BadRequest entity) {
      Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchDatasetProvidersByProviderIdResponse(responseBuilder.build(), entity);
    }

    public static PatchDatasetProvidersByProviderIdResponse respond401WithApplicationJson(
        Unauthorized entity) {
      Response.ResponseBuilder responseBuilder = Response.status(401).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchDatasetProvidersByProviderIdResponse(responseBuilder.build(), entity);
    }

    public static PatchDatasetProvidersByProviderIdResponse respond403WithApplicationJson(
        Forbidden entity) {
      Response.ResponseBuilder responseBuilder = Response.status(403).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchDatasetProvidersByProviderIdResponse(responseBuilder.build(), entity);
    }

    public static PatchDatasetProvidersByProviderIdResponse respond404WithApplicationJson(
        NotFound entity) {
      Response.ResponseBuilder responseBuilder = Response.status(404).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchDatasetProvidersByProviderIdResponse(responseBuilder.build(), entity);
    }

    public static PatchDatasetProvidersByProviderIdResponse respond422WithApplicationJson(
        UnprocessableEntity entity) {
      Response.ResponseBuilder responseBuilder = Response.status(422).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchDatasetProvidersByProviderIdResponse(responseBuilder.build(), entity);
    }

    public static PatchDatasetProvidersByProviderIdResponse respond500WithApplicationJson(
        Server entity) {
      Response.ResponseBuilder responseBuilder = Response.status(500).header("Content-Type", "application/json");
      responseBuilder.entity(entity);
      return new PatchDatasetProvidersByProviderIdResponse(responseBuilder.build(), entity);
    }
  }
}

package org.veupathdb.service.access.controller;

import java.util.LinkedHashMap;
import java.util.List;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.veupathdb.lib.container.jaxrs.server.annotations.Authenticated;
import org.veupathdb.service.access.generated.model.DatasetProviderCreateRequest;
import org.veupathdb.service.access.generated.model.DatasetProviderCreateResponseImpl;
import org.veupathdb.service.access.generated.model.DatasetProviderPatch;
import org.veupathdb.service.access.generated.resources.DatasetProviders;
import org.veupathdb.service.access.util.Keys;

import static org.veupathdb.service.access.service.provider.ProviderService.*;
import static org.veupathdb.service.access.service.staff.StaffService.userIsOwner;

@Authenticated
public class ProviderController implements DatasetProviders
{
  private final Request request;

  public ProviderController(@Context Request request) {
    this.request = request;
  }

  @Override
  public GetDatasetProvidersResponse getDatasetProviders(
    final String datasetId,
    final int limit,
    final int offset
  ) {
    Util.requireDatasetId(datasetId);

    final var currentUser = Util.requireUser(request);

    var allowed   = false;
    var providers = getProviderList(datasetId, limit, offset);

    // Determine if the user is a manager for the dataset.
    for (var pro : providers.getData()) {
      if (pro.getUser().getUserId() == currentUser.getUserId() && pro.getIsManager()) {
        allowed = true;
        break;
      }
    }

    // if the user is not a manager for the dataset, see if they are an owner.
    if (!allowed)
      allowed = userIsOwner(currentUser.getUserId());

    // If the user is neither an owner or a manager of the dataset, 403
    if (!allowed)
      throw new ForbiddenException();

    return GetDatasetProvidersResponse.respond200WithApplicationJson(providers);
  }

  @Override
  public PostDatasetProvidersResponse postDatasetProviders(
    final DatasetProviderCreateRequest entity
  ) {
    final var currentUser = Util.requireUser(request);

    validateCreate(entity);

    // To add a new provider, a user must be a site owner or a manager for the
    // dataset.
    if (!userIsOwner(currentUser.getUserId()) && !userIsManager(currentUser.getUserId(), entity.getDatasetId()))
      throw new ForbiddenException();

    // TODO: Connect to application database to verify datasetId value.
    final var out = new DatasetProviderCreateResponseImpl();
    out.setProviderId(createProvider(entity));

    return PostDatasetProvidersResponse.respond200WithApplicationJson(out);
  }

  @Override
  @SuppressWarnings("unchecked")
  public PatchDatasetProvidersByProviderIdResponse patchDatasetProvidersByProviderId(
    int providerId,
    List <DatasetProviderPatch> entity
  ) {
    final var currentUser = Util.requireUser(request);

    validatePatch(entity);
    final var provider = requireProviderById(providerId);

    // To add a new provider, a user must be a site owner or a manager for the
    // dataset.
    if (!userIsOwner(currentUser.getUserId()) && !userIsManager(currentUser.getUserId(), provider.getDatasetId()))
      throw new ForbiddenException();

    // WARNING: This cast mess is due to a bug in the JaxRS generator, the type
    // it actually passes up is not the declared type, but a list of linked hash
    // maps instead.
    final var item = ((List< LinkedHashMap <String, Object> >)((Object) entity)).get(0);


    provider.setManager((boolean) item.get(Keys.Json.KEY_VALUE));
    updateProvider(provider);

    return PatchDatasetProvidersByProviderIdResponse.respond204();
  }

  @Override
  public DeleteDatasetProvidersByProviderIdResponse deleteDatasetProvidersByProviderId(
    int providerId
  ) {
    if (!userIsOwner(request))
      throw new ForbiddenException();

    // Lookup will 404 if the provider id is invalid.
    requireProviderById(providerId);
    deleteProvider(providerId);

    return DeleteDatasetProvidersByProviderIdResponse.respond204();
  }
}

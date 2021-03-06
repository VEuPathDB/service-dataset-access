package org.veupathdb.service.access.controller;

import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.veupathdb.lib.container.jaxrs.server.annotations.Authenticated;
import org.veupathdb.service.access.generated.model.DatasetProviderCreateRequest;
import org.veupathdb.service.access.generated.model.DatasetProviderPatch;
import org.veupathdb.service.access.generated.resources.DatasetProviders;
import org.veupathdb.service.access.service.provider.ProviderService;

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
    final var currentUser = Util.requireUser(request);

    if (datasetId == null || datasetId.isBlank())
      throw new BadRequestException("datasetId query param is required");

    return GetDatasetProvidersResponse.respond200WithApplicationJson(
      getProviderList(datasetId, limit, offset, currentUser));
  }

  @Override
  public PostDatasetProvidersResponse postDatasetProviders(
    final DatasetProviderCreateRequest entity
  ) {
    return PostDatasetProvidersResponse.respond200WithApplicationJson(
      ProviderService.getInstance().createNewProvider(entity, Util.requireUser(request)));
  }

  @Override
  public PatchDatasetProvidersByProviderIdResponse patchDatasetProvidersByProviderId(
    final int providerId,
    final List <DatasetProviderPatch> entity
  ) {
    final var currentUser = Util.requireUser(request);

    ProviderService.getInstance().validatePatchRequest(entity);

    final var provider = requireProviderById(providerId);

    // To add a new provider, a user must be a site owner or a manager for the
    // dataset.
    if (!userIsOwner(currentUser.getUserId()) && !userIsManager(currentUser.getUserId(), provider.getDatasetId()))
      throw new ForbiddenException();

    provider.setManager(entity.get(0).getValue());
    updateProvider(provider);

    return PatchDatasetProvidersByProviderIdResponse.respond204();
  }

  @Override
  public DeleteDatasetProvidersByProviderIdResponse deleteDatasetProvidersByProviderId(
    final int providerId
  ) {
    if (!userIsOwner(request))
      throw new ForbiddenException();

    // Lookup will 404 if the provider id is invalid.
    requireProviderById(providerId);
    deleteProvider(providerId);

    return DeleteDatasetProvidersByProviderIdResponse.respond204();
  }
}

package org.veupathdb.service.access.controller;

import java.util.List;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.veupathdb.lib.container.jaxrs.providers.UserProvider;
import org.veupathdb.lib.container.jaxrs.server.annotations.Authenticated;
import org.veupathdb.service.access.generated.model.DatasetEndUsersGetApproval;
import org.veupathdb.service.access.generated.model.EndUserPatch;
import org.veupathdb.service.access.generated.resources.DatasetEndUsers;
import org.veupathdb.service.access.service.ProviderService;
import org.veupathdb.service.access.service.StaffService;

import static org.veupathdb.service.access.service.ProviderService.userIsManager;
import static org.veupathdb.service.access.service.StaffService.userIsOwner;

@Authenticated
public class EndUserController implements DatasetEndUsers
{
  private final Request request;

  public EndUserController(@Context Request request) {
    this.request = request;
  }

  @Override
  public GetDatasetEndUsersResponse getDatasetEndUsers(
    final String datasetId,
    final int limit,
    final int offset,
    final DatasetEndUsersGetApproval approval
  ) {
    final var curUser = UserProvider.lookupUser(request)
      .orElseThrow(InternalServerErrorException::new);

    if (!userIsManager(curUser.getUserId()) && !userIsOwner(curUser.getUserId()))
      throw new ForbiddenException();



    return null;
  }

  @Override
  public PostDatasetEndUsersResponse postDatasetEndUsers() {
    return null;
  }

  @Override
  public GetDatasetEndUsersByEndUserIdResponse getDatasetEndUsersByEndUserId(int endUserId) {
    return null;
  }

  @Override
  public PatchDatasetEndUsersByEndUserIdResponse patchDatasetEndUsersByEndUserId(
    int endUserId, List < EndUserPatch > entity
  ) {
    return null;
  }
}

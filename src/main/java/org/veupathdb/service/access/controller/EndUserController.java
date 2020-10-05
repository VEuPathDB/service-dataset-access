package org.veupathdb.service.access.controller;

import java.util.List;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.veupathdb.lib.container.jaxrs.server.annotations.Authenticated;
import org.veupathdb.service.access.generated.model.ApprovalStatus;
import org.veupathdb.service.access.generated.model.EndUserCreateRequest;
import org.veupathdb.service.access.generated.model.EndUserPatch;
import org.veupathdb.service.access.generated.resources.DatasetEndUsers;
import org.veupathdb.service.access.service.provider.ProviderService;
import org.veupathdb.service.access.service.user.EndUserCreationService;
import org.veupathdb.service.access.service.user.EndUserLookupService;
import org.veupathdb.service.access.service.user.EndUserPatchService;
import org.veupathdb.service.access.service.user.EndUserSearchService;

import static org.veupathdb.service.access.service.provider.ProviderService.userIsManager;
import static org.veupathdb.service.access.service.staff.StaffService.userIsOwner;

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
    final ApprovalStatus approval
  ) {
    return GetDatasetEndUsersResponse.respond200WithApplicationJson(
      EndUserSearchService.getInstance().findEndUsers(datasetId, limit, offset, approval, request));
  }

  @Override
  public PostDatasetEndUsersResponse postDatasetEndUsers(final EndUserCreateRequest entity) {
    return PostDatasetEndUsersResponse.respond200WithApplicationJson(
      EndUserCreationService.getInstance().handleUserCreation(entity, request));
  }

  @Override
  public GetDatasetEndUsersByEndUserIdResponse getDatasetEndUsersByEndUserId(
    final String endUserId
  ) {
    final var curUser = Util.requireUser(request);
    final var endUser = EndUserLookupService.getEndUser(endUserId);

    if (endUser.getUser().getUserId() == curUser.getUserId()
      || ProviderService.getInstance().isUserProvider(curUser.getUserId(), endUser.getDatasetId())
      || userIsOwner(curUser.getUserId())
    ) {
      return GetDatasetEndUsersByEndUserIdResponse.respond200WithApplicationJson(
        endUser);
    }

    throw new ForbiddenException();
  }

  @Override
  public PatchDatasetEndUsersByEndUserIdResponse patchDatasetEndUsersByEndUserId(
    final String endUserId,
    final List < EndUserPatch > entity
  ) {
    final var curUser = Util.requireUser(request);
    final var endUser = EndUserLookupService.getRawEndUser(endUserId);

    if (endUser.getUserId() == curUser.getUserId()) {
      EndUserPatchService.selfPatch(endUser, entity);
    } else if (userIsManager(curUser.getUserId(), endUser.getDatasetId()) || userIsOwner(curUser.getUserId())) {
      EndUserPatchService.modPatch(endUser, entity);
    } else {
      throw new ForbiddenException();
    }

    return PatchDatasetEndUsersByEndUserIdResponse.respond204();
  }
}

package org.veupathdb.service.access.controller;

import java.util.List;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.veupathdb.lib.container.jaxrs.providers.UserProvider;
import org.veupathdb.lib.container.jaxrs.server.annotations.Authenticated;
import org.veupathdb.service.access.generated.model.ApprovalStatus;
import org.veupathdb.service.access.generated.model.EndUserCreateRequest;
import org.veupathdb.service.access.generated.model.EndUserCreateResponseImpl;
import org.veupathdb.service.access.generated.model.EndUserPatch;
import org.veupathdb.service.access.generated.resources.DatasetEndUsers;
import org.veupathdb.service.access.service.EndUserService;

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
    final ApprovalStatus approval
  ) {
    final var curUser = UserProvider.lookupUser(request)
      .orElseThrow(InternalServerErrorException::new);

    if (!userIsManager(curUser.getUserId()) && !userIsOwner(curUser.getUserId()))
      throw new ForbiddenException();

    return GetDatasetEndUsersResponse.respond200WithApplicationJson(
      EndUserService.listEndUsers(datasetId, limit, offset, approval));
  }

  @Override
  public PostDatasetEndUsersResponse postDatasetEndUsers(
    final EndUserCreateRequest entity
  ) {
    final var curUser = UserProvider.lookupUser(request)
      .orElseThrow(InternalServerErrorException::new);

    final String recordId;
    if (curUser.getUserId() == entity.getUserId()) {
      EndUserService.validateOwnPost(entity);
      recordId = EndUserService.endUserSelfCreate(entity);
    } else if (userIsManager(curUser.getUserId()) || userIsOwner(curUser.getUserId())) {
      EndUserService.validateManagerPost(entity);
      recordId = EndUserService.endUserManagerCreate(entity);
    } else
      throw new ForbiddenException();

    final var out = new EndUserCreateResponseImpl();
    out.setEndUserId(recordId);

    return PostDatasetEndUsersResponse.respond200WithApplicationJson(out);
  }

  @Override
  public GetDatasetEndUsersByEndUserIdResponse getDatasetEndUsersByEndUserId(
    final String endUserId
  ) {
    final var curUser = UserProvider.lookupUser(request)
      .orElseThrow(InternalServerErrorException::new);

    final var endUser = EndUserService.getEndUser(endUserId);

    if (endUser.getUser().getUserId() == curUser.getUserId()
      || userIsManager(curUser.getUserId())
      ||userIsOwner(curUser.getUserId())
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
    final var curUser = UserProvider.lookupUser(request)
      .orElseThrow(InternalServerErrorException::new);

    final var endUser = EndUserService.getRawEndUser(endUserId);

    if (endUser.getUserId() == curUser.getUserId()) {
      EndUserService.selfPatch(endUser, entity);
    } else if (userIsManager(curUser.getUserId()) || userIsOwner(curUser.getUserId())) {
      EndUserService.modPatch(endUser, entity);
    } else {
      throw new ForbiddenException();
    }

    return null;
  }
}

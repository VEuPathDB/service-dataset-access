package org.veupathdb.service.access.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.veupathdb.lib.container.jaxrs.errors.UnprocessableEntityException;
import org.veupathdb.lib.container.jaxrs.providers.UserProvider;
import org.veupathdb.lib.container.jaxrs.server.annotations.Authenticated;
import org.veupathdb.service.access.generated.model.ApprovalStatus;
import org.veupathdb.service.access.generated.model.EndUserCreateRequest;
import org.veupathdb.service.access.generated.model.EndUserPatch;
import org.veupathdb.service.access.generated.resources.DatasetEndUsers;
import org.veupathdb.service.access.service.EndUserService;
import org.veupathdb.service.access.util.Keys;

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

    if (curUser.getUserId() == entity.getUserId())
      EndUserService.validateOwnPost(entity);
    else if (userIsManager(curUser.getUserId()) || userIsOwner(curUser.getUserId())) {
      // If the specified user does not exist then 422
      if (!EndUserService.userExists(entity.getUserId()))
        throw new UnprocessableEntityException(new HashMap <>(1){{
          put(Keys.Json.KEY_USER_ID, new ArrayList <>(1){{
            add("invalid user id");
          }});
        }});
    } else
      throw new ForbiddenException();


    return null;
  }

  @Override
  public GetDatasetEndUsersByEndUserIdResponse getDatasetEndUsersByEndUserId(
    String endUserId
  ) {
    final var curUser = UserProvider.lookupUser(request)
      .orElseThrow(InternalServerErrorException::new);

    if (!userIsManager(curUser.getUserId()) && !userIsOwner(curUser.getUserId()))
      throw new ForbiddenException();

    return null;
  }

  @Override
  public PatchDatasetEndUsersByEndUserIdResponse patchDatasetEndUsersByEndUserId(
    final String endUserId,
    final List < EndUserPatch > entity
  ) {
    return null;
  }
}

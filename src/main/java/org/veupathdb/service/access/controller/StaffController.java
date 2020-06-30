package org.veupathdb.service.access.controller;

import java.util.List;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

import org.veupathdb.lib.container.jaxrs.server.annotations.Authenticated;
import org.veupathdb.service.access.generated.model.NewStaffRequest;
import org.veupathdb.service.access.generated.model.NewStaffResponseImpl;
import org.veupathdb.service.access.generated.model.StaffPatch;
import org.veupathdb.service.access.generated.resources.Staff;
import org.veupathdb.service.access.service.StaffService;

@Authenticated
public class StaffController implements Staff
{
  private final Request request;

  public StaffController(@Context Request request) {
    this.request = request;
  }

  @Override
  public GetStaffResponse getStaff(int limit, int offset) {
    if (!StaffService.userIsOwner(request))
      throw new ForbiddenException();

    return GetStaffResponse.respond200WithApplicationJson(
      StaffService.getStaffList(limit, offset));
  }

  @Override
  public PostStaffResponse postStaff(final NewStaffRequest entity) {
    if (!StaffService.userIsOwner(request))
      throw new ForbiddenException();

    final var out = new NewStaffResponseImpl();
    out.setStaffId(StaffService.createStaff(entity));

    return PostStaffResponse.respond200WithApplicationJson(out);
  }

  @Override
  public PatchStaffByStaffIdResponse patchStaffByStaffId(
    final int staffId,
    final List < StaffPatch > entity
  ) {
    if (!StaffService.userIsOwner(request))
      throw new ForbiddenException();

    StaffService.validatePatch(entity);

    final var row = StaffService.requireStaffById(staffId);

    row.setOwner(entity.get(0).getValue());
    StaffService.updateStaffRow(row);

    return PatchStaffByStaffIdResponse.respond204();
  }

  @Override
  public DeleteStaffByStaffIdResponse deleteStaffByStaffId(final int staffId) {
    if (!StaffService.userIsOwner(request))
      throw new ForbiddenException();

    StaffService.deleteStaff(staffId);

    return DeleteStaffByStaffIdResponse.respond204();
  }
}

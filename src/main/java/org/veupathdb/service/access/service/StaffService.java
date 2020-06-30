package org.veupathdb.service.access.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.*;
import javax.ws.rs.core.Request;

import org.veupathdb.lib.container.jaxrs.providers.UserProvider;
import org.veupathdb.service.access.generated.model.*;
import org.veupathdb.service.access.model.PartialStaffRow;
import org.veupathdb.service.access.model.StaffRow;
import org.veupathdb.service.access.repo.StaffRepo;
import org.veupathdb.service.access.util.Keys;

public class StaffService
{
  /**
   * Looks up the current user and checks if they are a site owner.
   *
   * @return whether or not the current user is a site owner.
   */
  public static boolean userIsOwner(Request req) {
    return userIsOwner(UserProvider.lookupUser(req)
      .orElseThrow(InternalServerErrorException::new)
      .getUserId());
  }

  /**
   * Looks up whether the given userId belongs to a site owner.
   *
   * @return whether or not the given userId belongs to a site owner.
   */
  public static boolean userIsOwner(long userId) {
    try {
      return StaffRepo.Select.byUserId(userId)
        .filter(StaffRow::isOwner)
        .isPresent();
    } catch (WebApplicationException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static void updateStaffRow(final StaffRow row) {
    try {
      StaffRepo.Update.ownerFlagById(row);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static StaffList getStaffList(int limit, int offset) {
    try {
      return rows2StaffList(
        StaffRepo.Select.list(limit, offset),
        offset,
        StaffRepo.Select.count());
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static StaffRow requireStaffById(final int staffId) {
    try {
      return StaffRepo.Select.byId(staffId).orElseThrow(NotFoundException::new);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static int createStaff(final NewStaffRequest req) {
    final var row = new PartialStaffRow();
    row.setUserId(req.getUserId());
    row.setOwner(req.getIsOwner());

    try {
      return StaffRepo.Insert.newStaff(row);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static void deleteStaff(final int staffId) {
    try {
      StaffRepo.Delete.byId(staffId);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static void validatePatch(List < StaffPatch > entity) {
    if (entity.isEmpty())
      throw new BadRequestException();

    if (entity.size() > 1)
      throw new ForbiddenException();

    final var mod = entity.get(0);

    if (!"replace".equals(mod.getOp()))
      throw new ForbiddenException();

    if (!("/" + Keys.Json.KEY_IS_OWNER).equals(mod.getPath()))
      throw new ForbiddenException();
  }

  private static StaffList rows2StaffList(
    final List < StaffRow > rows,
    final int offset,
    final int total
  ) {
    final var out = new StaffListImpl();

    out.setOffset(offset);
    out.setTotal(total);
    out.setRows(rows.size());
    out.setData(rows.stream()
      .map(StaffService::row2Staff)
      .collect(Collectors.toList()));

    return out;
  }

  private static Staff row2Staff(final StaffRow row) {
    final var user = new UserDetailsImpl();
    user.setOrganization(row.getOrganization());
    user.setFirstName(row.getFirstName());
    user.setLastName(row.getLastName());
    user.setUserId(row.getUserId());

    final var out = new StaffImpl();
    out.setIsOwner(row.isOwner());
    out.setStaffId(row.getStaffId());
    out.setUser(user);

    return out;
  }
}

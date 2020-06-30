package org.veupathdb.service.access.service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;

import org.veupathdb.service.access.generated.model.*;
import org.veupathdb.service.access.model.ApprovalStatus;
import org.veupathdb.service.access.model.EndUserRow;
import org.veupathdb.service.access.model.RestrictionLevel;
import org.veupathdb.service.access.repo.AccountRepo;
import org.veupathdb.service.access.repo.EndUserRepo;
import org.veupathdb.service.access.util.Keys;

public class EndUserService
{
  public static EndUserList listEndUsers(
    final String datasetId,
    final int limit,
    final int offset,
    final org.veupathdb.service.access.generated.model.ApprovalStatus approval
  ) {
    try {
      if (approval == null) {
        return rows2EndUserList(
          EndUserRepo.Select.list(datasetId, limit, offset),
          offset,
          EndUserRepo.Select.countByDataset(datasetId)
        );
      }

      final var status = convertApproval(approval);

      return rows2EndUserList(
        EndUserRepo.Select.filteredList(datasetId, limit, offset, status),
        offset,
        EndUserRepo.Select.countByDatasetFiltered(datasetId, status)
      );
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  private static final String
    errEndUserForbidden = "end users cannot set the %s field";

  /**
   * Validate the creation request for a user that is self-requesting access.
   *
   * Users that are self-requesting access can only provide a subset of the
   * possible fields.  This method checks that the fields that are not allowed
   * to be set by an end user are not set in the request.
   */
  public static void validateOwnPost(final EndUserCreateRequest req) {
    if (req.getStartDate() != null)
      throw new ForbiddenException(String.format(
        errEndUserForbidden, Keys.Json.KEY_START_DATE));
    if (req.getDuration() != 0)
      throw new ForbiddenException(String.format(
        errEndUserForbidden, Keys.Json.KEY_DURATION));
    if (req.getRestrictionLevel() != null)
      throw new ForbiddenException(String.format(
        errEndUserForbidden, Keys.Json.KEY_RESTRICTION_LEVEL));
    if (req.getApprovalStatus() != null)
      throw new ForbiddenException(String.format(
        errEndUserForbidden, Keys.Json.KEY_APPROVAL_STATUS));
    if (req.getDenialReason() != null)
      throw new ForbiddenException(String.format(
        errEndUserForbidden, Keys.Json.KEY_DENIAL_REASON));
  }

  public static boolean userExists(final long userId) {
    try {
      return AccountRepo.Select.userExists(userId);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  private static ApprovalStatus convertApproval(
    final org.veupathdb.service.access.generated.model.ApprovalStatus status
  ) {
    return switch (status) {
      case APPROVED -> ApprovalStatus.APPROVED;
      case DENIED -> ApprovalStatus.DENIED;
      case REQUESTED -> ApprovalStatus.REQUESTED;
    };
  }

  private static org.veupathdb.service.access.generated.model.ApprovalStatus convertApproval(
    final ApprovalStatus status
  ) {
    return switch (status) {
      case APPROVED -> org.veupathdb.service.access.generated.model.ApprovalStatus.APPROVED;
      case DENIED -> org.veupathdb.service.access.generated.model.ApprovalStatus.DENIED;
      case REQUESTED -> org.veupathdb.service.access.generated.model.ApprovalStatus.REQUESTED;
    };
  }

  private static org.veupathdb.service.access.generated.model.RestrictionLevel
  convertRestriction(
    final RestrictionLevel level
  ) {
    return switch(level) {
      case ADMIN -> org.veupathdb.service.access.generated.model.RestrictionLevel.ADMIN;
      case CONTROLLED -> org.veupathdb.service.access.generated.model.RestrictionLevel.CONTROLLED;
      case LIMITED -> org.veupathdb.service.access.generated.model.RestrictionLevel.LIMITED;
      case PROTECTED -> org.veupathdb.service.access.generated.model.RestrictionLevel.PROTECTED;
      case PUBLIC -> org.veupathdb.service.access.generated.model.RestrictionLevel.PUBLIC;
    };
  }

  private static EndUserList rows2EndUserList(
    final List < EndUserRow > rows,
    final int offset,
    final int total
  ) {
    final var out = new EndUserListImpl();

    out.setOffset(offset);
    out.setTotal(total);
    out.setRows(rows.size());
    out.setData(rows.stream()
      .map(EndUserService::row2EndUser)
      .collect(Collectors.toList()));

    return out;
  }

  private static EndUser row2EndUser(final EndUserRow row) {
    final var user = new UserDetailsImpl();
    user.setUserId(row.getUserId());
    user.setLastName(row.getLastName());
    user.setFirstName(row.getFirstName());
    user.setOrganization(row.getOrganization());

    final var out = new EndUserImpl();
    out.setUser(user);
    out.setAnalysisPlan(row.getAnalysisPlan());
    out.setApprovalStatus(convertApproval(row.getApprovalStatus()));
    out.setDenialReason(row.getDenialReason());
    out.setDisseminationPlan(row.getDisseminationPlan());
    out.setDuration(row.getDuration());
    out.setPurpose(row.getPurpose());
    out.setResearchQuestion(row.getResearchQuestion());
    out.setRestrictionLevel(convertRestriction(row.getRestrictionLevel()));
    out.setStartDate(Date.from(row.getStartDate().toInstant()));

    return out;
  }
}

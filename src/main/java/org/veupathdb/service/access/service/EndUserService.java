package org.veupathdb.service.access.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.InternalServerErrorException;

import org.veupathdb.service.access.generated.model.*;
import org.veupathdb.service.access.model.ApprovalStatus;
import org.veupathdb.service.access.model.EndUserRow;
import org.veupathdb.service.access.repo.EndUserRepo;

public class EndUserService
{
  public static EndUserList listEndUsers(
    final String datasetId,
    final int limit,
    final int offset,
    final DatasetEndUsersGetApproval approval
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

  public static ApprovalStatus convertApproval(
    final DatasetEndUsersGetApproval status
  ) {
    return switch (status) {
      case APPROVED -> ApprovalStatus.APPROVED;
      case DENIED -> ApprovalStatus.DENIED;
      case REQUESTED -> ApprovalStatus.REQUESTED;
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

    // TODO: Fill in the rest.

    return out;
  }
}

package org.veupathdb.service.access.service.user;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.ws.rs.*;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.access.generated.model.EndUser;
import org.veupathdb.service.access.generated.model.EndUserCreateRequest;
import org.veupathdb.service.access.generated.model.EndUserList;
import org.veupathdb.service.access.generated.model.EndUserPatch;
import org.veupathdb.service.access.generated.model.EndUserPatch.OpType;
import org.veupathdb.service.access.model.ApprovalStatus;
import org.veupathdb.service.access.model.EndUserRow;
import org.veupathdb.service.access.model.RestrictionLevel;
import org.veupathdb.service.access.repo.AccountRepo;
import org.veupathdb.service.access.repo.DatasetRepo;
import org.veupathdb.service.access.util.Keys;

public class EndUserService
{
  @SuppressWarnings("FieldMayBeFinal")
  private static EndUserService instance = new EndUserService();

  private final Logger log = LogProvider.logger(EndUserService.class);

  EndUserService() {
  }

  /**
   * Returns an {@link EndUserList} result containing at most <code>limit</code>
   * end users that have access to the given <code>datasetId</code> starting at
   * result <code>offset + 1</code>, optionally filtered by an approval status.
   *
   * @param datasetId ID of the dataset for which to find end users.
   * @param limit     Max number of results to return
   * @param offset    Record offset.
   * @param approval  Optional (nullable) approval status filter.
   *
   * @return limited result set to return to the client.
   */
  public EndUserList findEndUsers(
    final String datasetId,
    final int limit,
    final int offset,
    final org.veupathdb.service.access.generated.model.ApprovalStatus approval
  ) {
    log.trace("EndUserService#listEndUsers(String, int, int, ApprovalStatus)");

    try {
      if (approval == null) {
        return EndUserUtil.rows2EndUserList(
          EndUserRepo.Select.list(datasetId, limit, offset),
          offset,
          EndUserRepo.Select.countByDataset(datasetId)
        );
      }

      final var status = EndUserUtil.convertApproval(approval);

      return EndUserUtil.rows2EndUserList(
        EndUserRepo.Select.filteredList(datasetId, limit, offset, status),
        offset,
        EndUserRepo.Select.countByDatasetFiltered(datasetId, status)
      );
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static EndUserList listEndUsers(
    final String datasetId,
    final int limit,
    final int offset,
    final org.veupathdb.service.access.generated.model.ApprovalStatus approval
  ) {
    return getInstance().findEndUsers(datasetId, limit, offset, approval);
  }


  public EndUser lookupEndUser(final String rawId) {
    log.trace("EndUserService#getEndUser(String)");

    final var id = new EndUserId(rawId);

    try {
      return EndUserUtil.row2EndUser(EndUserRepo.Select.endUser(id.userId, id.datasetId)
        .orElseThrow(NotFoundException::new));
    } catch (WebApplicationException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static EndUser getEndUser(final String rawId) {
    return getInstance().lookupEndUser(rawId);
  }


  public EndUserRow lookupRawEndUser(final String rawId) {
    log.trace("EndUserService#getRawEndUser(String)");

    final var id = new EndUserId(rawId);

    try {
      return EndUserRepo.Select.endUser(id.userId, id.datasetId)
        .orElseThrow(BadRequestException::new);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static EndUserRow getRawEndUser(final String rawId) {
    return getInstance().lookupRawEndUser(rawId);
  }


  /**
   * Inserts a new end user record with defaulted values in place of data that
   * cannot be self-set by end users.
   */
  public String endUserSelfCreate(final EndUserCreateRequest req) {
    log.trace("EndUserService#endUserSelfCreate(EndUserCreateRequest)");
    try {
      final var row = EndUserUtil.createRequest2EndUserRow(req)
        .setApprovalStatus(ApprovalStatus.REQUESTED)
        .setRestrictionLevel(RestrictionLevel.PUBLIC)
        .setStartDate(OffsetDateTime.now())
        .setDuration(-1);

      EndUserRepo.Insert.newEndUser(row);

      return formatEndUserId(row.getUserId(), row.getDatasetId());
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static String userSelfCreate(final EndUserCreateRequest req) {
    return getInstance().endUserSelfCreate(req);
  }


  /**
   * Inserts a new end user record.
   * <p>
   * Provides defaults for the following fields if no value was set in the
   * request:
   * <ul>
   *   <li>startDate</li>
   *   <li>approvalStatus</li>
   *   <li>restrictionLevel</li>
   *   <li>duration</li>
   * </ul>
   */
  public String endUserManagerCreate(final EndUserCreateRequest req) {
    log.trace("EndUserService#endUserManagerCreate(EndUserCreateRequest)");
    try {
      final var row = EndUserUtil.createRequest2EndUserRow(req);

      if (req.getStartDate() == null) {
        log.debug("defaulting start date");
        row.setStartDate(OffsetDateTime.now());
      }

      if (req.getApprovalStatus() == null) {
        log.debug("defaulting approval status");
        row.setApprovalStatus(ApprovalStatus.REQUESTED);
      }

      if (req.getRestrictionLevel() == null) {
        log.debug("defaulting restriction level");
        row.setRestrictionLevel(RestrictionLevel.PUBLIC);
      }

      row.setAllowSelfEdits(false);

      if (req.getDuration() == 0)
        row.setDuration(-1);

      EndUserRepo.Insert.newEndUser(row);

      return formatEndUserId(row.getUserId(), row.getDatasetId());
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static String endUserMgrCreate(final EndUserCreateRequest req) {
    return getInstance().endUserManagerCreate(req);
  }


  public boolean checkEndUserExists(final long userId, final String datasetId) {
    log.trace("EndUserService#checkEndUserExists(long, String)");

    try {
      return EndUserRepo.Select.endUser(userId, datasetId).isPresent();
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static boolean endUserExists(final long userId, final String datasetId) {
    return getInstance().checkEndUserExists(userId, datasetId);
  }


  /**
   * Returns whether or not the given userId belongs to an already existing user
   * record.
   */
  public boolean userExists(final long userId) {
    log.trace("EndUserService#userExists(long)");

    try {
      return AccountRepo.Select.userExists(userId);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public String formatEndUserId(final long userId, final String datasetId) {
    log.trace("EndUserService#formatEndUserId(long, String)");

    return userId + "-" + datasetId;
  }

  public void applySelfPatch(
    final EndUserRow row,
    final List< EndUserPatch > patches
  ) {
    log.trace("EndUserService#selfPatch(EndUserRow, List)");
    var pVal = new Patch();

    if (patches == null || patches.isEmpty())
      throw new BadRequestException();

    for (var patch : patches) {
      // End users are only permitted to perform "replace" patch operations.
      pVal.enforceOpIn(patch, OpType.REPLACE);

      switch (patch.getPath().substring(1)) {
        case Keys.Json.KEY_PURPOSE -> pVal.strVal(patch, row::setPurpose);
        case Keys.Json.KEY_RESEARCH_QUESTION -> pVal.strVal(patch, row::setResearchQuestion);
        case Keys.Json.KEY_ANALYSIS_PLAN -> pVal.strVal(patch, row::setAnalysisPlan);
        case Keys.Json.KEY_DISSEMINATION_PLAN -> pVal.strVal(patch, row::setDisseminationPlan);
        case Keys.Json.KEY_PRIOR_AUTH -> pVal.strVal(patch, row::setPriorAuth);

        // do nothing
        default -> throw pVal.forbiddenOp(patch);
      }
    }

    try {
      EndUserRepo.Update.self(row);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static void selfPatch(final EndUserRow row, final List< EndUserPatch > patches) {
    getInstance().applySelfPatch(row, patches);
  }

  public void applyModPatch(final EndUserRow row, final List< EndUserPatch > patches) {
    log.trace("EndUserService#modPatch(row, patches)");
    var pVal = new Patch();

    if (patches.isEmpty())
      throw new BadRequestException();

    for (var patch : patches) {
      pVal.enforceOpIn(patch, OpType.ADD, OpType.REMOVE, OpType.REPLACE);

      // Remove the leading '/' character from the path.
      switch (patch.getPath().substring(1)) {
        case Keys.Json.KEY_START_DATE -> {
          if (patch.getValue() == null)
            row.setStartDate(null);
          else
            row.setStartDate(OffsetDateTime.parse(
              pVal.enforceType(patch.getValue(), String.class)));
        }
        case Keys.Json.KEY_DURATION -> {
          if (patch.getValue() == null)
            row.setDuration(-1);
          else
            row.setDuration(pVal.enforceType(patch.getValue(), Number.class).intValue());
        }
        case Keys.Json.KEY_PURPOSE -> pVal.strVal(patch, row::setPurpose);
        case Keys.Json.KEY_RESEARCH_QUESTION -> pVal.strVal(patch, row::setResearchQuestion);
        case Keys.Json.KEY_ANALYSIS_PLAN -> pVal.strVal(patch, row::setAnalysisPlan);
        case Keys.Json.KEY_DISSEMINATION_PLAN -> pVal.strVal(patch, row::setDisseminationPlan);
        case Keys.Json.KEY_PRIOR_AUTH -> pVal.strVal(patch, row::setPriorAuth);
        case Keys.Json.KEY_RESTRICTION_LEVEL -> pVal.enumVal(
          patch,
          RestrictionLevel::valueOf,
          row::setRestrictionLevel
        );
        case Keys.Json.KEY_APPROVAL_STATUS -> pVal.enumVal(
          patch,
          ApprovalStatus::valueOf,
          row::setApprovalStatus
        );
        case Keys.Json.KEY_DENIAL_REASON -> pVal.strVal(patch, row::setDenialReason);
        default -> throw pVal.forbiddenOp(patch);
      }
    }

    try {
      EndUserRepo.Update.mod(row);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static void modPatch(final EndUserRow row, final List< EndUserPatch > patches) {
    getInstance().applyModPatch(row, patches);
  }

  public static EndUserService getInstance() {
    return instance;
  }

  boolean datasetExists(final String datasetId) {
    log.trace("EndUserService#datasetExists(datasetId)");

    try {
      return DatasetRepo.Select.datasetExists(datasetId);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  private static class EndUserId
  {
    private final long userId;
    private final String datasetId;

    private EndUserId(final String rawId) {
      final var ind = rawId.indexOf('-');

      if (ind == -1)
        throw new BadRequestException();

      try {
        userId = Long.parseLong(rawId.substring(0, ind));
      } catch (Exception e) {
        throw new BadRequestException(e);
      }

      datasetId = rawId.substring(ind + 1);
    }
  }

  private static class Patch
  {
    private static final String
      errBadPatchOp = "Cannot perform operation \"%s\" on field \"%s\".",
      errSetNull    = "Cannot set field \"%s\" to null.",
      errBadType    = "Expected a value of type \"%s\", got \"%s\"";

    private final Logger log = LogProvider.logger(getClass());

    private < T > T enforceType(
      final Object value,
      final Class< T > type
    ) {
      log.trace("EndUserService$Patch#enforceType(value, type)");

      try {
        return type.cast(value);
      } catch (Exception e) {
        final String name;
        if (type.getName().endsWith("[]") || Collection.class.isAssignableFrom(
          type))
          name = "array";
        else if (type.equals(String.class))
          name = "string";
        else if (Number.class.isAssignableFrom(type))
          name = "number";
        else if (type.equals(Boolean.class))
          name = "boolean";
        else
          name = "object";

        throw new BadRequestException(String.format(errBadType, name,
          value.getClass().getSimpleName()
        ));
      }
    }

    private void strVal(
      final EndUserPatch patch,
      final Consumer< String > func
    ) {
      log.trace("EndUserService$Patch#strVal(patch, func)");

      switch (patch.getOp()) {
        case ADD, REPLACE:
          enforceNotNull(patch);
          func.accept(enforceType(
            patch.getValue(),
            String.class
          ));
        case REMOVE:
          func.accept(null);
        default:
          throw forbiddenOp(patch);
      }
    }

    private < T > void enumVal(
      final EndUserPatch patch,
      final Function< String, T > map,
      final Consumer< T > func
    ) {
      log.trace("EndUserService$Patch#enumVal(patch, map, func)");

      enforceNotNull(patch);
      func.accept(map.apply(enforceType(patch, String.class).toUpperCase()));
    }


    private void enforceOpIn(
      final EndUserPatch patch,
      final OpType... in
    ) {
      log.trace("EndUserService$Patch#enforceOpIn(patch, ...in)");

      for (final var i : in) {
        if (i.equals(patch.getOp()))
          return;
      }

      throw forbiddenOp(patch);
    }

    private RuntimeException forbiddenOp(final EndUserPatch op) {
      return new ForbiddenException(
        String.format(
          errBadPatchOp,
          op.getOp().name(),
          op.getPath()
        ));
    }

    private void enforceNotNull(final EndUserPatch patch) {
      log.trace("EndUserService$Patch#enforceNotNull(patch)");

      if (patch.getValue() == null)
        throw new ForbiddenException(
          String.format(errSetNull, patch.getPath()));
    }
  }
}

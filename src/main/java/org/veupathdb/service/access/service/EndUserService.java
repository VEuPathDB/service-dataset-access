package org.veupathdb.service.access.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ws.rs.*;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.errors.UnprocessableEntityException;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.access.generated.model.*;
import org.veupathdb.service.access.generated.model.EndUserPatch.OpType;
import org.veupathdb.service.access.model.ApprovalStatus;
import org.veupathdb.service.access.model.EndUserRow;
import org.veupathdb.service.access.model.RestrictionLevel;
import org.veupathdb.service.access.repo.AccountRepo;
import org.veupathdb.service.access.repo.DatasetRepo;
import org.veupathdb.service.access.repo.EndUserRepo;
import org.veupathdb.service.access.util.Keys;

public class EndUserService
{
  private static final Logger log = LogProvider.logger(EndUserService.class);

  /**
   * Error messages
   */
  private static final String
    errEndUserForbidden = "End users cannot set the %s field",
    errBadDatasetId     = "Invalid dataset id";

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
  public static EndUserList listEndUsers(
    final String datasetId,
    final int limit,
    final int offset,
    final org.veupathdb.service.access.generated.model.ApprovalStatus approval
  ) {
    log.trace("EndUserService#listEndUsers(datasetId, limit, offset, approval)");
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

  public static EndUser getEndUser(final String rawId) {
    log.trace("EndUserService#getEndUser(rawId)");
    final var id = new EndUserId(rawId);

    try {
      return row2EndUser(EndUserRepo.Select.endUser(id.userId, id.datasetId)
        .orElseThrow(NotFoundException::new));
    } catch (WebApplicationException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static EndUserRow getRawEndUser(final String rawId) {
    log.trace("EndUserService#getRawEndUser(rawId)");
    final var id = new EndUserId(rawId);

    try {
      return EndUserRepo.Select.endUser(id.userId, id.datasetId)
        .orElseThrow(BadRequestException::new);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  /**
   * Inserts a new end user record with defaulted values in place of data that
   * cannot be self-set by end users.
   */
  public static String endUserSelfCreate(final EndUserCreateRequest req) {
    log.trace("EndUserService#endUserSelfCreate(req)");
    try {
      final var row = createRequest2EndUserRow(req)
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
  public static String endUserManagerCreate(final EndUserCreateRequest req) {
    log.trace("EndUserService#endUserManagerCreate(req)");
    try {
      final var row = createRequest2EndUserRow(req);

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

      if (req.getDuration() == 0)
        row.setDuration(-1);

      EndUserRepo.Insert.newEndUser(row);

      return formatEndUserId(row.getUserId(), row.getDatasetId());
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static boolean endUserExists(final long userId, final String datasetId) {
    try {
      return EndUserRepo.Select.endUser(userId, datasetId).isPresent();
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  /**
   * Validates the creation request for a user that is self-requesting access.
   * <p>
   * Performs the following validations:
   * <ol>
   *   <li>The user is not attempting to set an auth start date.</li>
   *   <li>The user is not attempting to set an auth duration.</li>
   *   <li>The user is not attempting to set the restriction level value.</li>
   *   <li>The user is not attempting to set the approval status value.</li>
   *   <li>The user is not attempting to set the denial reason value.</li>
   *   <li>The target dataset exists.</li>
   * </ol>
   */
  public static void validateOwnPost(final EndUserCreateRequest req) {
    log.trace("EndUserService#validateOwnPost(req)");

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

    validateTextFields(req);
  }

  /**
   * Validates the request body for an end user creation posted by a site owner
   * or dataset manager.
   * <p>
   * Performs the following validations:
   * <ol>
   *   <li>User ID is valid</li>
   *   <li>Dataset ID is valid</li>
   * </ol>
   */
  public static void validateManagerPost(final EndUserCreateRequest req) {
    log.trace("EndUserService#validateManagerPost(req)");
    if (!userExists(req.getUserId()))
      throw new UnprocessableEntityException(
        new HashMap < String, List < String > >(1)
        {{
          put(Keys.Json.KEY_USER_ID, new ArrayList <>(1)
          {{
            add("invalid user id");
          }});
        }});

    validateTextFields(req);
  }

  /**
   * Returns whether or not the given userId belongs to an already existing user
   * record.
   */
  public static boolean userExists(final long userId) {
    log.trace("EndUserService#userExists(userId)");
    try {
      return AccountRepo.Select.userExists(userId);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static String formatEndUserId(
    final long userId,
    final String datasetId
  ) {
    log.trace("EndUserService#formatEndUserId(userId, datasetId)");
    return userId + "-" + datasetId;
  }

  @SuppressWarnings("unchecked")
  public static void selfPatch(
    final EndUserRow row,
    final List < EndUserPatch > patches
  ) {
    log.trace("EndUserService#selfPatch(row, patches)");

    if (patches == null || patches.isEmpty())
      throw new BadRequestException();

    // WARNING: This cast mess is due to a bug in the JaxRS generator, the type
    // it actually passes up is not the declared type, but a list of linked hash
    // maps instead.
    final var items = ((List < Map < String, Object > >) ((Object) patches));

    for (var patch : items) {
      // End users are only permitted to perform "replace" patch operations.
      Patch.enforceOpIn(patch, OpType.REPLACE);

      switch (((String) patch.get(Keys.Json.KEY_PATH)).substring(1)) {
        case Keys.Json.KEY_PURPOSE:
          Patch.strVal(patch, row::setPurpose);
        case Keys.Json.KEY_RESEARCH_QUESTION:
          Patch.strVal(patch, row::setResearchQuestion);
        case Keys.Json.KEY_ANALYSIS_PLAN:
          Patch.strVal(patch, row::setAnalysisPlan);
        case Keys.Json.KEY_DISSEMINATION_PLAN:
          Patch.strVal(patch, row::setDisseminationPlan);
        case Keys.Json.KEY_PRIOR_AUTH:
          Patch.strVal(patch, row::setPriorAuth);

          // do nothing
        default:
          throw Patch.forbiddenOp(patch);
      }
    }

    try {
      EndUserRepo.Update.self(row);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static void modPatch(
    final EndUserRow row,
    final List < EndUserPatch > patches
  ) {
    log.trace("EndUserService#modPatch(row, patches)");

    if (patches.isEmpty())
      throw new BadRequestException();

    // WARNING: This cast mess is due to a bug in the JaxRS generator, the type
    // it actually passes up is not the declared type, but a list of linked hash
    // maps instead.
    final var items = ((List < Map < String, Object > >) ((Object) patches));

    for (var patch : items) {
      Patch.enforceOpIn(patch, OpType.ADD, OpType.REMOVE, OpType.REPLACE);

      // Remove the leading '/' character from the path.
      switch (((String) patch.get(Keys.Json.KEY_PATH)).substring(1)) {
        case Keys.Json.KEY_START_DATE:
          if (patch.get(Keys.Json.KEY_VALUE) == null)
            row.setStartDate(null);
          else
            row.setStartDate(OffsetDateTime.parse(
              Patch.enforceType(patch.get(Keys.Json.KEY_VALUE), String.class)));
        case Keys.Json.KEY_DURATION:
          if (patch.get(Keys.Json.KEY_VALUE) == null)
            row.setDuration(-1);
          else
            row.setDuration(Patch.enforceType(patch.get(Keys.Json.KEY_VALUE), Number.class)
              .intValue());
        case Keys.Json.KEY_PURPOSE:
          Patch.strVal(patch, row::setPurpose);
        case Keys.Json.KEY_RESEARCH_QUESTION:
          Patch.strVal(patch, row::setResearchQuestion);
        case Keys.Json.KEY_ANALYSIS_PLAN:
          Patch.strVal(patch, row::setAnalysisPlan);
        case Keys.Json.KEY_DISSEMINATION_PLAN:
          Patch.strVal(patch, row::setDisseminationPlan);
        case Keys.Json.KEY_PRIOR_AUTH:
          Patch.strVal(patch, row::setPriorAuth);
        case Keys.Json.KEY_RESTRICTION_LEVEL:
          Patch.enumVal(
            patch,
            RestrictionLevel::valueOf,
            row::setRestrictionLevel
          );
        case Keys.Json.KEY_APPROVAL_STATUS:
          Patch.enumVal(patch, ApprovalStatus::valueOf, row::setApprovalStatus);
        case Keys.Json.KEY_DENIAL_REASON:
          Patch.strVal(patch, row::setDenialReason);
        default:
          throw Patch.forbiddenOp(patch);
      }
    }

    try {
      EndUserRepo.Update.mod(row);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  private static boolean datasetExists(final String datasetId) {
    log.trace("EndUserService#datasetExists(datasetId)");
    try {
      return DatasetRepo.Select.datasetExists(datasetId);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  /**
   * Converts from generated to internal approval status enum type.
   */
  private static ApprovalStatus convertApproval(
    final org.veupathdb.service.access.generated.model.ApprovalStatus status
  ) {
    log.trace("EndUserService#convertApproval(status)");
    return status == null
      ? null
      : switch (status) {
        case APPROVED -> ApprovalStatus.APPROVED;
        case DENIED -> ApprovalStatus.DENIED;
        case REQUESTED -> ApprovalStatus.REQUESTED;
      };
  }

  /**
   * Converts from internal to generated approval status enum type.
   */
  private static org.veupathdb.service.access.generated.model.ApprovalStatus convertApproval(
    final ApprovalStatus status
  ) {
    log.trace("EndUserService#convertApproval(status)");
    return status == null
      ? null
      : switch (status) {
        case APPROVED -> org.veupathdb.service.access.generated.model.ApprovalStatus.APPROVED;
        case DENIED -> org.veupathdb.service.access.generated.model.ApprovalStatus.DENIED;
        case REQUESTED -> org.veupathdb.service.access.generated.model.ApprovalStatus.REQUESTED;
      };
  }

  private static RestrictionLevel convertRestriction(
    final org.veupathdb.service.access.generated.model.RestrictionLevel level
  ) {
    log.trace("EndUserService#convertRestriction(level)");
    return level == null
      ? null
      : switch (level) {
        case ADMIN -> RestrictionLevel.ADMIN;
        case CONTROLLED -> RestrictionLevel.CONTROLLED;
        case LIMITED -> RestrictionLevel.LIMITED;
        case PROTECTED -> RestrictionLevel.PROTECTED;
        case PUBLIC -> RestrictionLevel.PUBLIC;
      };
  }

  /**
   * Converts from internal to generated restriction level enum type.
   */
  private static org.veupathdb.service.access.generated.model.RestrictionLevel
  convertRestriction(
    final RestrictionLevel level
  ) {
    log.trace("EndUserService#convertRestriction(level)");
    return level == null
      ? null
      : switch (level) {
        case ADMIN -> org.veupathdb.service.access.generated.model.RestrictionLevel.ADMIN;
        case CONTROLLED -> org.veupathdb.service.access.generated.model.RestrictionLevel.CONTROLLED;
        case LIMITED -> org.veupathdb.service.access.generated.model.RestrictionLevel.LIMITED;
        case PROTECTED -> org.veupathdb.service.access.generated.model.RestrictionLevel.PROTECTED;
        case PUBLIC -> org.veupathdb.service.access.generated.model.RestrictionLevel.PUBLIC;
      };
  }

  @SuppressWarnings("deprecation")
  private static EndUserRow createRequest2EndUserRow(
    final EndUserCreateRequest req
  ) {
    log.trace("EndUserService#createRequest2EndUserRow(req)");

    OffsetDateTime start = null;

    if (req.getStartDate() != null)
      start = req.getStartDate()
        .toInstant()
        .atOffset(ZoneOffset.ofHoursMinutes(
          req.getStartDate().getTimezoneOffset() / 60,
          req.getStartDate().getTimezoneOffset() % 60
        ));

    return (EndUserRow) new EndUserRow()
      .setDatasetId(req.getDatasetId())
      .setStartDate(start)
      .setDuration(req.getDuration())
      .setPurpose(req.getPurpose())
      .setResearchQuestion(req.getResearchQuestion())
      .setAnalysisPlan(req.getAnalysisPlan())
      .setDisseminationPlan(req.getDisseminationPlan())
      .setPriorAuth(req.getPriorAuth())
      .setRestrictionLevel(convertRestriction(req.getRestrictionLevel()))
      .setApprovalStatus(convertApproval(req.getApprovalStatus()))
      .setDenialReason(req.getDenialReason())
      .setUserId(req.getUserId());
  }

  /**
   * Converts a list of {@link EndUserRow} instances into an instance of the
   * generated type {@link EndUserList}.
   *
   * @param rows   List of rows to convert.
   * @param offset Pagination/row offset value.
   * @param total  Total number of possible results.
   *
   * @return converted end user list.
   */
  private static EndUserList rows2EndUserList(
    final List < EndUserRow > rows,
    final int offset,
    final int total
  ) {
    log.trace("EndUserService#rows2EndUserList(rows, offset, total)");
    final var out = new EndUserListImpl();

    out.setOffset(offset);
    out.setTotal(total);
    out.setRows(rows.size());
    out.setData(rows.stream()
      .map(EndUserService::row2EndUser)
      .collect(Collectors.toList()));

    return out;
  }

  /**
   * Converts an {@link EndUserRow} instance into an instance of the generated
   * type {@link EndUser}.
   *
   * @param row end user data to convert
   *
   * @return converted end user data
   */
  private static EndUser row2EndUser(final EndUserRow row) {
    log.trace("EndUserService#row2EndUser(row)");

    final var user = new UserDetailsImpl();
    user.setUserId(row.getUserId());
    user.setLastName(row.getLastName());
    user.setFirstName(row.getFirstName());
    user.setOrganization(row.getOrganization());

    final var out = new EndUserImpl();
    out.setUser(user);
    out.setDatasetId(row.getDatasetId());
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

  private static void validateTextFields(EndUserCreateRequest req) {
    final var validation = new HashMap<String, List<String>>();

    if (req.getPurpose() == null || req.getPurpose().isBlank())
      validation.put(Keys.Json.KEY_PURPOSE, new ArrayList <>(){{
        add("The purpose field cannot be blank.");
      }});

    if (req.getResearchQuestion() == null || req.getResearchQuestion().isBlank())
      validation.put(Keys.Json.KEY_RESEARCH_QUESTION, new ArrayList <>(){{
        add("The research question field cannot be blank.");
      }});

    if (req.getAnalysisPlan() == null || req.getAnalysisPlan().isBlank())
      validation.put(Keys.Json.KEY_ANALYSIS_PLAN, new ArrayList <>(){{
        add("The analysis plan field cannot be blank.");
      }});

    if (req.getDisseminationPlan() == null || req.getDisseminationPlan().isBlank())
      validation.put(Keys.Json.KEY_DISSEMINATION_PLAN, new ArrayList <>(){{
        add("The dissemination plan field cannot be blank.");
      }});

    if (!datasetExists(req.getDatasetId()))
      validation.put(Keys.Json.KEY_DATASET_ID, new ArrayList <>(1)
      {{
        add(errBadDatasetId);
      }});

    if (!validation.isEmpty())
      throw new UnprocessableEntityException(validation);
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

    private static < T > T enforceType(
      final Object value,
      final Class < T > type
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

    private static void strVal(
      final Map<String, Object> patch,
      final Consumer < String > func
    ) {
      log.trace("EndUserService$Patch#strVal(patch, func)");

      final var op = (String) patch.get(Keys.Json.KEY_OP);
      switch (op) {
        case "add", "replace" -> {
          enforceNotNull(patch);
          func.accept(enforceType(
            patch.get(Keys.Json.KEY_VALUE),
            String.class
          ));
        }
        case "remove" -> func.accept(null);
        default -> throw forbiddenOp(patch);
      }
    }

    private static < T > void enumVal(
      final Map<String, Object> patch,
      final Function < String, T > map,
      final Consumer < T > func
    ) {
      log.trace("EndUserService$Patch#enumVal(patch, map, func)");

      enforceNotNull(patch);
      func.accept(map.apply(enforceType(patch, String.class).toUpperCase()));
    }

    private static void enforceNotNull(final Map<String, Object> patch) {
      log.trace("EndUserService$Patch#enforceNotNull(patch)");

      if (patch.get(Keys.Json.KEY_VALUE) == null)
        throw new ForbiddenException(
          String.format(errSetNull, patch.get(Keys.Json.KEY_PATH)));
    }

    private static void enforceOpIn(
      final Map < String, Object > patch,
      final OpType... in
    ) {
      log.trace("EndUserService$Patch#enforceOpIn(patch, ...in)");

      final var op = OpType.valueOf(((String) patch.get(Keys.Json.KEY_OP)).toUpperCase());

      for (final var i : in) {
        if (i.equals(op))
          return;
      }

      throw forbiddenOp(patch);
    }

    private static RuntimeException forbiddenOp(final Map<String, Object> op) {
      return new ForbiddenException(
        String.format(
          errBadPatchOp,
          op.get(Keys.Json.KEY_OP),
          op.get(Keys.Json.KEY_PATH)));
    }
  }
}

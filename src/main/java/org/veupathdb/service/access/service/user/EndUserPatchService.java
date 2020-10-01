package org.veupathdb.service.access.service.user;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.ws.rs.*;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.access.generated.model.EndUserPatch;
import org.veupathdb.service.access.generated.model.EndUserPatch.OpType;
import org.veupathdb.service.access.model.ApprovalStatus;
import org.veupathdb.service.access.model.EndUserRow;
import org.veupathdb.service.access.model.RestrictionLevel;
import org.veupathdb.service.access.service.email.EmailService;
import org.veupathdb.service.access.util.Keys;

public class EndUserPatchService
{
  @SuppressWarnings("FieldMayBeFinal")
  private static EndUserPatchService instance = new EndUserPatchService();

  private final Logger log = LogProvider.logger(EndUserPatchService.class);

  EndUserPatchService() {
  }

  // ╔════════════════════════════════════════════════════════════════════╗ //
  // ║                                                                    ║ //
  // ║    End User Lookup Handling                                        ║ //
  // ║                                                                    ║ //
  // ╚════════════════════════════════════════════════════════════════════╝ //

  public void applySelfPatch(
    final EndUserRow row,
    final List<EndUserPatch> patches
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

  public static void selfPatch(final EndUserRow row, final List<EndUserPatch> patches) {
    getInstance().applySelfPatch(row, patches);
  }

  public void applyModPatch(final EndUserRow row, final List<EndUserPatch> patches) {
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
//      EmailService.getInstance().sendEndUserUpdateNotificationEmail();
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static void modPatch(final EndUserRow row, final List<EndUserPatch> patches) {
    getInstance().applyModPatch(row, patches);
  }

  public static EndUserPatchService getInstance() {
    return instance;
  }

  private static class Patch
  {
    private static final String
      errBadPatchOp = "Cannot perform operation \"%s\" on field \"%s\".",
      errSetNull    = "Cannot set field \"%s\" to null.",
      errBadType    = "Expected a value of type \"%s\", got \"%s\"";

    private final Logger log = LogProvider.logger(getClass());

    private <T> T enforceType(
      final Object value,
      final Class<T> type
    ) {
      log.trace("EndUserService$Patch#enforceType(value, type)");

      try {
        return type.cast(value);
      } catch (Exception e) {
        final String name;
        if (type.getName().endsWith("[]") || Collection.class.isAssignableFrom(type))
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
      final Consumer<String> func
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

    private <T> void enumVal(
      final EndUserPatch patch,
      final Function<String, T> map,
      final Consumer<T> func
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

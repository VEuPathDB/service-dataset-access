package org.veupathdb.service.access.service.user;

import java.time.OffsetDateTime;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.access.generated.model.EndUserPatch;
import org.veupathdb.service.access.generated.model.EndUserPatch.OpType;
import org.veupathdb.service.access.model.ApprovalStatus;
import org.veupathdb.service.access.model.EndUserRow;
import org.veupathdb.service.access.model.RestrictionLevel;
import org.veupathdb.service.access.model.UserRow;
import org.veupathdb.service.access.service.dataset.DatasetRepo;
import org.veupathdb.service.access.service.email.EmailService;
import org.veupathdb.service.access.service.provider.ProviderRepo;
import org.veupathdb.service.access.util.Keys;

public class EndUserPatchService
{
  private static final String
    ERR_NOT_EDITABLE = "This record is not marked as editable.";

  @SuppressWarnings("FieldMayBeFinal")
  private static EndUserPatchService instance;

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
    var pVal = new PatchUtil();

    if (patches == null || patches.isEmpty())
      throw new BadRequestException();

    if (!row.isAllowSelfEdits())
      throw new ForbiddenException(ERR_NOT_EDITABLE);

    for (var patch : patches) {
      // End users are only permitted to perform "replace" patch operations.
      pVal.enforceOpIn(patch, OpType.REPLACE);

      switch (patch.getPath().substring(1)) {
        case Keys.Json.KEY_PURPOSE
          -> pVal.strVal(patch, row::setPurpose);

        case Keys.Json.KEY_RESEARCH_QUESTION
          -> pVal.strVal(patch, row::setResearchQuestion);

        case Keys.Json.KEY_ANALYSIS_PLAN
          -> pVal.strVal(patch, row::setAnalysisPlan);

        case Keys.Json.KEY_DISSEMINATION_PLAN
          -> pVal.strVal(patch, row::setDisseminationPlan);

        case Keys.Json.KEY_PRIOR_AUTH
          -> pVal.strVal(patch, row::setPriorAuth);

        // do nothing
        default -> throw pVal.forbiddenOp(patch);
      }
    }

    // End users are only allowed to edit their access request once without a
    // manager or provider stepping in to re-enable self edits.
    row.setAllowSelfEdits(false);

    try {
      final var ds = DatasetRepo.Select.getInstance()
        .selectDataset(row.getDatasetId())
        .orElseThrow();
      final var ccs = ProviderRepo.Select.byDataset(row.getDatasetId(), 100, 0)
        .stream()
        .map(UserRow::getEmail)
        .toArray(String[]::new);
      EndUserRepo.Update.self(row);
      EmailService.getInstance()
        .sendEndUserUpdateNotificationEmail(ccs, ds, row);
    } catch (WebApplicationException e) {
      throw e;
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }
  }

  public static void selfPatch(final EndUserRow row, final List<EndUserPatch> patches) {
    getInstance().applySelfPatch(row, patches);
  }

  public void applyModPatch(final EndUserRow row, final List<EndUserPatch> patches) {
    log.trace("EndUserService#modPatch(row, patches)");
    var pVal = new PatchUtil();

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

  public static void modPatch(final EndUserRow row, final List<EndUserPatch> patches) {
    getInstance().applyModPatch(row, patches);
  }

  public static EndUserPatchService getInstance() {
    if (instance == null)
      instance = new EndUserPatchService();

    return instance;
  }
}

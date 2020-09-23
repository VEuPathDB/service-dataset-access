package org.veupathdb.service.access.service.user;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.ForbiddenException;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.errors.UnprocessableEntityException;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.access.generated.model.EndUserCreateRequest;
import org.veupathdb.service.access.util.Keys;

public class EndUserValidate
{
  @SuppressWarnings("FieldMayBeFinal")
  private static EndUserValidate instance = new EndUserValidate();

  /**
   * Error messages
   */
  private static final String
    errEndUserForbidden = "End users cannot set the %s field",
    errBadDatasetId     = "Invalid dataset id";


  private final Logger log = LogProvider.logger(getClass());

  EndUserValidate() {}

  public static EndUserValidate getInstance() {
    return instance;
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
  public void validateSelfCreateRequest(final EndUserCreateRequest req) {
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

  public static void validateSelfCreate(final EndUserCreateRequest req) {
    getInstance().validateSelfCreateRequest(req);
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
  public void validateManagerPost(final EndUserCreateRequest req) {
    log.trace("EndUserValidate#validateManagerPost(EndUserCreateRequest)");

    if (!EndUserService.getInstance().userExists(req.getUserId()))
      throw new UnprocessableEntityException(Collections.singletonMap(
        Keys.Json.KEY_USER_ID,
        Collections.singletonList("invalid user id")
      ));

    validateTextFields(req);
  }

  public static void validateMgrPost(final EndUserCreateRequest req) {
    getInstance().validateManagerPost(req);
  }

  void validateTextFields(final EndUserCreateRequest req) {
    log.trace("EndUserValidate#validateTextFields(EndUserCreateRequest)");
    final var validation = new HashMap< String, List< String > >();

    if (req.getPurpose() == null || req.getPurpose().isBlank())
      validation.put(
        Keys.Json.KEY_PURPOSE,
        Collections.singletonList("The purpose field cannot be blank.")
      );

    if (req.getResearchQuestion() == null || req.getResearchQuestion().isBlank())
      validation.put(
        Keys.Json.KEY_RESEARCH_QUESTION,
        Collections.singletonList("The research question field cannot be blank.")
      );

    if (req.getAnalysisPlan() == null || req.getAnalysisPlan().isBlank())
      validation.put(
        Keys.Json.KEY_ANALYSIS_PLAN,
        Collections.singletonList("The analysis plan field cannot be blank.")
      );

    if (req.getDisseminationPlan() == null || req.getDisseminationPlan().isBlank())
      validation.put(
        Keys.Json.KEY_DISSEMINATION_PLAN,
        Collections.singletonList("The dissemination plan field cannot be blank.")
      );

    if (!EndUserService.getInstance().datasetExists(req.getDatasetId()))
      validation.put(Keys.Json.KEY_DATASET_ID, Collections.singletonList(errBadDatasetId));

    if (!validation.isEmpty())
      throw new UnprocessableEntityException(validation);
  }

}

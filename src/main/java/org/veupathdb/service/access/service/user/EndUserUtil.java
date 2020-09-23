package org.veupathdb.service.access.service.user;

import java.sql.ResultSet;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.veupathdb.lib.container.jaxrs.providers.LogProvider;
import org.veupathdb.service.access.generated.model.*;
import org.veupathdb.service.access.model.*;
import org.veupathdb.service.access.model.ApprovalStatus;
import org.veupathdb.service.access.model.RestrictionLevel;
import org.veupathdb.service.access.repo.DB;

public class EndUserUtil
{
  private static final Logger log = LogProvider.logger(EndUserUtil.class);

  /**
   * Parses a single {@link ResultSet} row into an instance of
   * {@link EndUserRow}.
   */
  static EndUserRow parseEndUserRow(final ResultSet rs) throws Exception {
    log.trace("EndUserRepo$Select#parseEndUserRow(ResultSet)");

    return (EndUserRow) new EndUserRow()
      .setAnalysisPlan(rs.getString(DB.Column.EndUser.AnalysisPlan))
      .setApprovalStatus(ApprovalStatusCache.getInstance()
        .get(rs.getShort(DB.Column.EndUser.ApprovalStatus))
        .orElseThrow())
      .setDatasetId(rs.getString(DB.Column.EndUser.DatasetId))
      .setDisseminationPlan(rs.getString(DB.Column.EndUser.DisseminationPlan))
      .setDuration(rs.getInt(DB.Column.EndUser.Duration))
      .setPriorAuth(rs.getString(DB.Column.EndUser.PriorAuth))
      .setPurpose(rs.getString(DB.Column.EndUser.Purpose))
      .setResearchQuestion(rs.getString(DB.Column.EndUser.ResearchQuestion))
      .setRestrictionLevel(RestrictionLevelCache.getInstance()
        .get(rs.getShort(DB.Column.EndUser.RestrictionLevel))
        .orElseThrow())
      .setStartDate(rs.getObject(DB.Column.EndUser.StartDate, OffsetDateTime.class))
      .setDenialReason(rs.getString(DB.Column.EndUser.DenialReason))
      .setDateDenied(rs.getObject(DB.Column.EndUser.DateDenied, OffsetDateTime.class))
      .setAllowSelfEdits(rs.getBoolean(DB.Column.EndUser.AllowSelfEdits))
      .setUserId(rs.getLong(DB.Column.EndUser.UserId))
      .setEmail(rs.getString(DB.Column.Accounts.Email))
      .setOrganization(rs.getString(DB.Column.Misc.Organization))
      .setFirstName(rs.getString(DB.Column.Misc.FirstName))
      .setLastName(rs.getString(DB.Column.Misc.LastName));
  }

  /**
   * Converts from generated to internal approval status enum type.
   */
  static ApprovalStatus convertApproval(
    final org.veupathdb.service.access.generated.model.ApprovalStatus status
  ) {
    log.trace("EndUserService#convertApproval(ApprovalStatus)");

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
  static org.veupathdb.service.access.generated.model.ApprovalStatus convertApproval(
    final ApprovalStatus status
  ) {
    log.trace("EndUserService#convertApproval(ApprovalStatus)");
    return status == null
      ? null
      : switch (status) {
        case APPROVED -> org.veupathdb.service.access.generated.model.ApprovalStatus.APPROVED;
        case DENIED -> org.veupathdb.service.access.generated.model.ApprovalStatus.DENIED;
        case REQUESTED -> org.veupathdb.service.access.generated.model.ApprovalStatus.REQUESTED;
      };
  }

  static RestrictionLevel convertRestriction(
    final org.veupathdb.service.access.generated.model.RestrictionLevel level
  ) {
    log.trace("EndUserService#convertRestriction(RestrictionLevel)");
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
  static org.veupathdb.service.access.generated.model.RestrictionLevel convertRestriction(
    final RestrictionLevel level
  ) {
    log.trace("EndUserService#convertRestriction(RestrictionLevel)");
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
  static EndUserRow createRequest2EndUserRow(
    final EndUserCreateRequest req
  ) {
    log.trace("EndUserService#createRequest2EndUserRow(EndUserCreateRequest)");

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
  static EndUserList rows2EndUserList(
    final List<EndUserRow> rows,
    final int offset,
    final int total
  ) {
    log.trace("EndUserService#rows2EndUserList(List, int, int)");
    final var out = new EndUserListImpl();

    out.setOffset(offset);
    out.setTotal(total);
    out.setRows(rows.size());
    out.setData(rows.stream()
      .map(EndUserUtil::row2EndUser)
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
  static EndUser row2EndUser(final EndUserRow row) {
    log.trace("EndUserService#row2EndUser(EndUserRow)");

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
}

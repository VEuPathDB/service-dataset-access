package org.veupathdb.service.access.service.history.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

import io.vulpine.lib.query.util.basic.BasicPreparedListReadQuery;
import org.veupathdb.service.access.service.history.model.HistoryResultRow;

public class SelectHistoryRows
{
  private static final String SQL = "SELECT\n"
    + "  end_user_id\n"
    + ", user_id\n"
    + ", dataset_presenter_id\n"
    + ", history_action\n"
    + ", history_timestamp\n"
    + ", history_cause_user\n"
    + ", restriction_level_id\n"
    + ", approval_status_id\n"
    + ", start_date\n"
    + ", duration\n"
    + ", purpose\n"
    + ", research_question\n"
    + ", analysis_plan\n"
    + ", dissemination_plan\n"
    + ", prior_auth\n"
    + ", denial_reason\n"
    + ", date_denied\n"
    + ", allow_self_edits\n"
    + "FROM\n"
    + "  studyaccess.end_user_history\n"
    + "OFFSET ? ROWS\n"
    + "FETCH FIRST ? ROWS ONLY";

  private static final int HardLimit = 100;

  private final Connection con;
  private final int limit;
  private final int offset;

  public SelectHistoryRows(Connection con, int limit, int offset) {
    this.con    = Objects.requireNonNull(con);
    this.limit  = Math.min(Math.max(1, limit), HardLimit);
    this.offset = Math.max(0, offset);
  }

  public List<HistoryResultRow> run() throws Exception {
    return new BasicPreparedListReadQuery<>(SQL, con, HistoryResultRow::new, this::prep)
      .execute()
      .getValue();
  }

  private void prep(PreparedStatement ps) throws Exception {
    ps.setInt(1, offset);
    ps.setInt(2, limit);
  }
}

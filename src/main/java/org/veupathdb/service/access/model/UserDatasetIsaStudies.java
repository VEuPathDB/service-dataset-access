package org.veupathdb.service.access.model;

import org.gusdb.fgputil.db.runner.SQLRunner;
import org.veupathdb.lib.container.jaxrs.utils.db.DbManager;
import org.veupathdb.service.access.generated.model.*;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class UserDatasetIsaStudies {

  private static final String STUDY_ID_COL = "study_id";
  private static final String IS_OWNER_COL = "is_owner";

  private static final String USER_STUDY_SQL =
      "select " +
      "  studyds.study_stable_id as " + STUDY_ID_COL + ", " +
      "  dataset.is_owner as " + IS_OWNER_COL + " " +
      "from ( " +
      "    select user_dataset_id, 1 as is_owner " +
      "    from apidbuserdatasets.userdatasetowner " +
      "    where user_id = ? " +
      "    union " +
      "    select user_dataset_id, 0 as is_owner " +
      "    from apidbuserdatasets.userdatasetsharedwith " +
      "    where recipient_user_id = ? " +
      ") dataset, eda_ud.studydataset studyds " +
      "where studyds.user_dataset_id = dataset.user_dataset_id";

  private static Object[] userStudySqlParams(long userId) { return new Object[]{ userId, userId }; }
  private static final Integer[] USER_STUDY_SQL_PARAM_TYPES = new Integer[]{ Types.BIGINT, Types.BIGINT };

  /**
   * Looks up the ISA Study user datasets owned by or shared with the user
   * with the passed ID, converts them into a map of permission objects,
   * which it returns.
   *
   * @param userId user ID for which study permissions should be added
   */
  public static Map<String, DatasetPermissionEntry> getUserDatasetPermissions(long userId) {
    return new SQLRunner(DbManager.applicationDatabase().getDataSource(), USER_STUDY_SQL, "user_study_sql")
      .executeQuery(userStudySqlParams(userId), USER_STUDY_SQL_PARAM_TYPES, rs -> {
        Map<String, DatasetPermissionEntry> userStudies = new HashMap<>();
        while (rs.next()) {
          String studyId = rs.getString(STUDY_ID_COL);
          boolean isOwner = rs.getBoolean(IS_OWNER_COL);
          userStudies.put(studyId, createDatasetPermissionEntry(studyId, isOwner));
        }
        return userStudies;
      });
  }

  private static DatasetPermissionEntry createDatasetPermissionEntry(String studyId, boolean isOwner) {

    DatasetPermissionEntry permEntry = new DatasetPermissionEntryImpl();

    permEntry.setStudyId(studyId);
    permEntry.setSha1Hash(""); // FIXME: Not sure where to get this for user dataset studies

    // set permission type for this dataset
    permEntry.setType(isOwner ?
        DatasetPermissionLevel.PROVIDER :
        DatasetPermissionLevel.ENDUSER);

    permEntry.setIsManager(isOwner); // FIXME: Correct?  Maybe false all the time for user dataset studies

    // if study is owned by or has been shared with a user, full access is granted
    ActionList actions = new ActionListImpl();
    actions.setStudyMetadata(true);

    // controls search, visualizations, small results
    actions.setSubsetting(true);
    actions.setVisualizations(true);
    actions.setResultsFirstPage(true);

    // controls access to full dataset, downloads
    actions.setResultsAll(true);

    permEntry.setActionAuthorization(actions);
    return permEntry;
  }

}

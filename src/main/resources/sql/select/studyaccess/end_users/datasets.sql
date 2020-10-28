SELECT
  dataset_presenter_id
FROM
  studyaccess.end_users
WHERE
  approval_status_id = 0 -- 0 = approved
  AND user_id = ?

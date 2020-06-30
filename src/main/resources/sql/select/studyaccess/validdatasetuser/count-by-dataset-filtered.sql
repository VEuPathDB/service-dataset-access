SELECT
  COUNT(1)
FROM
  studyaccess.validdatasetuser v
WHERE
  v.dataset_presenter_id = ?
  AND v.approval_status = ?

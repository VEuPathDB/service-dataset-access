SELECT
  study.dataset_id
FROM
  apidbtuning.studyiddatasetid study
WHERE
  study.study_stable_id = ?

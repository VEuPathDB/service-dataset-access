SELECT
  p.dataset_presenter_id
, p.value
, s.study_stable_id
FROM
  apidbtuning.datasetproperty p,
  apidbtuning.studyiddatasetid s
WHERE
  p.dataset_presenter_id = s.dataset_id
AND
  p.property = 'studyAccess'
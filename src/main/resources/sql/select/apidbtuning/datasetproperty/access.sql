SELECT
  pres.dataset_presenter_id,
  pres.dataset_sha1_digest,
  prop.value,
  study.study_stable_id
FROM
  apidbtuning.datasetpresenter pres,
  apidbtuning.datasetproperty prop,
  apidbtuning.studyiddatasetid study
WHERE
  pres.dataset_presenter_id = study.dataset_id
AND
  pres.dataset_presenter_id = prop.dataset_presenter_id
AND
  prop.property = 'studyAccess'
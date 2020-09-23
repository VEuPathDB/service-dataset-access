SELECT
  property
, value
FROM
  apidbtuning.datasetproperty
WHERE
  dataset_presenter_id = ?
  AND property IN ('requestEmail', 'requestEmailBcc')

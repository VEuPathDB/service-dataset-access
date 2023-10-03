SELECT
  ds.*,
  COALESCE(prop.study_access,'public') as study_access,
  prop.days_for_approval,
  prop.custom_approval_email_body
FROM (
  SELECT
    study.study_stable_id,
    pres.dataset_presenter_id,
    pres.dataset_sha1_digest,
    pres.display_name,
    pres.short_display_name,
    pres.description
  FROM
    apidbtuning.datasetpresenter pres,
    apidbtuning.studyiddatasetid study
  WHERE
    pres.dataset_presenter_id = study.dataset_id
) ds
LEFT JOIN (
  SELECT
    dataset_presenter_id,
    MAX(DECODE(property, 'studyAccess', value)) AS study_access,
    MAX(DECODE(property, 'daysForApproval', value)) AS days_for_approval,
    MAX(DECODE(property, 'customApprovalEmailBody', value)) AS custom_approval_email_body
  FROM apidbtuning.datasetproperty
  GROUP BY dataset_presenter_id
) prop
ON
  ds.dataset_presenter_id = prop.dataset_presenter_id

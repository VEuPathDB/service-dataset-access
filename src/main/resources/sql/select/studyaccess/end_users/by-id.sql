SELECT
  v.user_id
, v.dataset_presenter_id
, v.start_date
, v.duration
, v.purpose
, v.research_question
, v.analysis_plan
, v.dissemination_plan
, v.prior_auth
, v.restriction_level_id
, v.approval_status_id
, v.denial_reason
, (
    SELECT
      value
    FROM
      useraccounts.account_properties
    WHERE
        user_id = v.user_id
    AND key = 'first_name'
  ) AS first_name
, (
    SELECT
      value
    FROM
      useraccounts.account_properties
    WHERE
        user_id = v.user_id
    AND key = 'last_name'
  ) AS last_name
, (
    SELECT
      value
    FROM
      useraccounts.account_properties
    WHERE
        user_id = v.user_id
    AND key = 'organization'
  ) AS organization
, a.email
FROM
  studyaccess.end_users v
  INNER JOIN useraccounts.accounts a
    ON v.user_id = a.user_id
WHERE
  v.user_id = ?
  AND v.dataset_presenter_id = ?

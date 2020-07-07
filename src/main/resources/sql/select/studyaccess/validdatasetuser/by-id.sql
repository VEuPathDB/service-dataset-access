SELECT
  v.*
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
  studyaccess.validdatasetuser v
  INNER JOIN useraccounts.accounts a
    ON v.user_id = a.user_id
WHERE
  v.user_id = ?
  AND v.dataset_presenter_id = ?

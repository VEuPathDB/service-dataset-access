SELECT
  p.user_id,
  p.project_id,
  p.dataset_id,
  a.email,
  (
    SELECT value
    FROM useraccounts.account_properties
    WHERE user_id = p.user_id
    AND key = 'first_name'
  ) first_name,
  (
    SELECT value
    FROM useraccounts.account_properties
    WHERE user_id = p.user_id
    AND key = 'last_name'
  )
FROM
  studyaccess.providers p
  INNER JOIN useraccounts.accounts a
    ON p.user_id = a.user_id
WHERE
    p.provider_id = ?

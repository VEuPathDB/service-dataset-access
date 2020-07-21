SELECT
  p.provider_id
, p.user_id
, a.email
, p.is_manager
, (
    SELECT value
    FROM useraccounts.account_properties
    WHERE user_id = p.user_id
    AND key = 'first_name'
  ) first_name
, (
    SELECT value
    FROM useraccounts.account_properties
    WHERE user_id = p.user_id
    AND key = 'last_name'
  ) last_name
, (
    SELECT value
    FROM useraccounts.account_properties
    WHERE user_id = p.user_id
    AND key = 'organization'
  ) organization
FROM
  studyaccess.providers p
  INNER JOIN useraccounts.accounts a
    ON p.user_id = a.user_id
WHERE
  p.dataset_id = ?
ORDER BY
  p.provider_id
OFFSET ? ROWS
FETCH NEXT ? ROWS ONLY

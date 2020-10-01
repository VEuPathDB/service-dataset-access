WITH
  dataset(id)  AS (SELECT ? FROM DUAL)
, limit  (val) AS (SELECT COALESCE(?, -1) FROM DUAL)
, offset (val) AS (SELECT COALESCE(?, 0) FROM DUAL)
, status (val) AS (SELECT ? FROM DUAL)
SELECT
  t.*
FROM
  (
    SELECT
      row_number() OVER (ORDER BY user_id, dataset_presenter_id) AS rn,
      u.*
    FROM
      studyaccess.validdatasetuser u
      INNER JOIN dataset d
        ON d.id IS NULL
        OR d.id = u.dataset_presenter_id
      INNER JOIN status s
        ON s.val IS NULL
        OR s.val = u.approval_status_id
  ) t
, limit
, offset
WHERE
  (
    limit.val = -1
    OR rn <= (limit.val + offset.val)
  )
  AND (
    offset.val = 0
    OR rn > offset.val
  )

UPDATE
  studyaccess.validdatasetuser
SET
  start_date = ?               -- 1, date
, duration = ?                 -- 2, long
, purpose = ?                  -- 3, string
, research_question = ?        -- 4, string
, analysis_plan = ?            -- 5, string
, dissemination_plan = ?       -- 6, string
, prior_auth = ?               -- 7, string
, restriction_level_id = ?     -- 8, short
, approval_status_id = ?       -- 9, short
, denial_reason = ?            -- 10, string
WHERE
  user_id = ?                  -- 11, long
  AND dataset_presenter_id = ? -- 12, string

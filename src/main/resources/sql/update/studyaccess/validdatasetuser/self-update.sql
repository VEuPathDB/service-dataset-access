UPDATE
  studyaccess.validdatasetuser
SET
  purpose = ?            -- 1, string
, research_question = ?  -- 2, string
, analysis_plan = ?      -- 3, string
, dissemination_plan = ? -- 4, string
, prior_auth = ?         -- 5, string
WHERE
  user_id = ?                  -- 6, long
  AND dataset_presenter_id = ? -- 7, string

INSERT INTO
  studyaccess.validdatasetuser (
    user_id               -- 1
  , dataset_presenter_id  -- 2
  , restriction_level_id  -- 3
  , approval_status_id    -- 4
  , start_date            -- 5
  , duration              -- 6
  , purpose               -- 7
  , research_question     -- 8
  , analysis_plan         -- 9
  , dissemination_plan    -- 10
  , prior_auth            -- 11
  , denial_reason         -- 12
  )
VALUES
  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)

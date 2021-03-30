-- studyaccess.approval_status

CREATE TABLE studyaccess.approval_status
(
  approval_status_id NUMBER(1) PRIMARY KEY,
  name               VARCHAR2(24) NOT NULL
);

-- studyaccess.restriction_level

CREATE TABLE studyaccess.restriction_level
(
  restriction_level_id NUMBER(1) GENERATED AS IDENTITY
    CONSTRAINT restriction_level_pk PRIMARY KEY,
  name                 VARCHAR2(24) UNIQUE NOT NULL
);

-- studyaccess.staff

CREATE TABLE studyaccess.staff
(
  staff_id NUMBER(8) GENERATED AS IDENTITY
    CONSTRAINT staff_pk PRIMARY KEY,
  user_id  NUMBER(12)          NOT NULL UNIQUE,
  is_owner NUMBER(1) DEFAULT 0 NOT NULL CHECK ( is_owner = 0 OR is_owner = 1 )
);

-- studyaccess.providers

CREATE TABLE studyaccess.providers
(
  provider_id NUMBER(8) GENERATED AS IDENTITY
    CONSTRAINT providers_pk PRIMARY KEY,
  user_id     NUMBER(12)          NOT NULL,
  is_manager  NUMBER(1) DEFAULT 0 NOT NULL CHECK ( is_manager = 0 OR is_manager = 1 ),
  dataset_id  VARCHAR2(15)        NOT NULL,
  CONSTRAINT provider_user_ds_uq UNIQUE (user_id, dataset_id)
);

-- studyaccess.end_users

CREATE TABLE studyaccess.end_users
(
  end_user_id          NUMBER(12) GENERATED AS IDENTITY
    CONSTRAINT end_users_pk PRIMARY KEY,
  user_id              NUMBER(12)                                         NOT NULL,
  dataset_presenter_id VARCHAR2(15)                                       NOT NULL,
  restriction_level_id NUMBER(1)                                          NOT NULL
    REFERENCES studyaccess.restriction_level (restriction_level_id),
  approval_status_id   NUMBER(1)                DEFAULT 1                 NOT NULL
    REFERENCES studyaccess.approval_status (approval_status_id),
  start_date           TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
  duration             NUMBER(12)               DEFAULT -1                NOT NULL,
  purpose              VARCHAR2(4000),
  research_question    VARCHAR2(4000),
  analysis_plan        VARCHAR2(4000),
  dissemination_plan   VARCHAR2(4000),
  prior_auth           VARCHAR2(4000),
  denial_reason        VARCHAR2(4000),
  date_denied          TIMESTAMP WITH TIME ZONE,
  allow_self_edits     NUMBER(1)                DEFAULT 0                 NOT NULL,
  CONSTRAINT end_user_ds_user_uq UNIQUE (user_id, dataset_presenter_id)
);

-- studyaccess.end_user_history
CREATE TABLE studyaccess.end_user_history
(
  end_user_id          NUMBER(12)                                         NOT NULL
    REFERENCES studyaccess.end_users (end_user_id),
  user_id              NUMBER(12)                                         NOT NULL,
  dataset_presenter_id VARCHAR2(15)                                       NOT NULL,
  -- Action taken on the record, should be one of: CREATE, UPDATE, or DELETE
  history_action       VARCHAR2(6)                                        NOT NULL,
  -- Timestamp of the change to the studyaccess.end_users table
  history_timestamp    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
  -- User who made the change to the studyaccess.end_users table.
  history_cause_user   NUMBER(12)                                         NOT NULL,
  restriction_level_id NUMBER(1)                                          NOT NULL
    REFERENCES studyaccess.restriction_level (restriction_level_id),
  approval_status_id   NUMBER(1)                                          NOT NULL
    REFERENCES studyaccess.approval_status (approval_status_id),
  start_date           TIMESTAMP WITH TIME ZONE                           NOT NULL,
  duration             NUMBER(12)                                         NOT NULL,
  purpose              VARCHAR2(4000),
  research_question    VARCHAR2(4000),
  analysis_plan        VARCHAR2(4000),
  dissemination_plan   VARCHAR2(4000),
  prior_auth           VARCHAR2(4000),
  denial_reason        VARCHAR2(4000),
  date_denied          TIMESTAMP WITH TIME ZONE,
  allow_self_edits     NUMBER(1)                                          NOT NULL
);

-----------------
-- PERMISSIONS --
-----------------

GRANT SELECT ON studyaccess.approval_status TO useraccts_w, useraccts_r;
GRANT SELECT ON studyaccess.restriction_level TO useraccts_w, useraccts_r;
GRANT SELECT ON studyaccess.staff TO useraccts_r;
GRANT SELECT, INSERT, UPDATE, DELETE ON studyaccess.staff TO useraccts_w;
GRANT SELECT ON studyaccess.providers TO useraccts_r;
GRANT SELECT, INSERT, UPDATE, DELETE ON studyaccess.providers TO useraccts_w;
GRANT SELECT ON studyaccess.end_users TO useraccts_r;
GRANT SELECT, INSERT, UPDATE, DELETE ON studyaccess.end_users TO useraccts_w;
GRANT SELECT ON studyaccess.end_user_history TO useraccts_r;
GRANT SELECT, INSERT, UPDATE, DELETE ON studyaccess.end_user_history TO useraccts_w;

----------
-- Data --
----------

INSERT INTO studyaccess.approval_status (approval_status_id, name) VALUES (0, 'approved');
INSERT INTO studyaccess.approval_status (approval_status_id, name) VALUES (1, 'requested');
INSERT INTO studyaccess.approval_status (approval_status_id, name) VALUES (2, 'denied');

INSERT INTO studyaccess.restriction_level (name) VALUES ('public');
INSERT INTO studyaccess.restriction_level (name) VALUES ('controlled');
INSERT INTO studyaccess.restriction_level (name) VALUES ('protected');
INSERT INTO studyaccess.restriction_level (name) VALUES ('prerelease');
INSERT INTO studyaccess.restriction_level (name) VALUES ('private');

-- Initial owner user ids
INSERT INTO studyaccess.staff (user_id, is_owner) VALUES (48, 1);
INSERT INTO studyaccess.staff (user_id, is_owner) VALUES (376, 1);
INSERT INTO studyaccess.staff (user_id, is_owner) VALUES (219825440, 1);
INSERT INTO studyaccess.staff (user_id, is_owner) VALUES (220902410, 1);
INSERT INTO studyaccess.staff (user_id, is_owner) VALUES (295652793, 1);
INSERT INTO studyaccess.staff (user_id, is_owner) VALUES (276765373, 1);

-- Migration from ClinEpi data to new service data
INSERT INTO
  studyaccess.end_users (
  user_id
, dataset_presenter_id
, restriction_level_id
, approval_status_id
, start_date
, duration
, purpose
, research_question
, analysis_plan
, dissemination_plan
, prior_auth
, denial_reason
, date_denied
, allow_self_edits)
SELECT
  user_id
, dataset_presenter_id
, (
  SELECT
    restriction_level_id
  FROM
    studyaccess.restriction_level
  WHERE
    name = (
      CASE vdu.restriction_level
        WHEN 'admin' THEN 'public'
        ELSE vdu.restriction_level
      END
    )
  )
, COALESCE(approval_status, 0)
, TO_TIMESTAMP_TZ(TO_CHAR(start_date, 'YYYY-MM-DD HH24:MI:SS ') || '00:00',
                  'YYYY-MM-DD HH24:MI:SS TZH:TZM')
, duration
, purpose
, research_question
, analysis_plan
, dissemination_plan
, prior_auth
, NULL
, NULL
, 0
FROM
  studyaccess.validdatasetuser vdu
;

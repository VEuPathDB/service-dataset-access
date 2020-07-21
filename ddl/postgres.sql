CREATE SCHEMA access;

-- Approval Status Table -------------------------------------------------------

CREATE TABLE access.approval_status
(
  approval_status_id SMALLSERIAL PRIMARY KEY,
  name               VARCHAR(24) NOT NULL UNIQUE
);

INSERT INTO
  access.approval_status (name)
VALUES
  ('approved')
, ('requested')
, ('denied');

-- Restriction Level Table -----------------------------------------------------

CREATE TABLE access.restriction_level
(
  restriction_level_id SMALLSERIAL PRIMARY KEY,
  name                 VARCHAR(24) NOT NULL UNIQUE
);

INSERT INTO
  access.restriction_level (name)
VALUES
  ('public')
, ('limited')
, ('protected')
, ('controlled')
, ('admin');

-- Staff Table -----------------------------------------------------------------

CREATE TABLE access.staff
(
  staff_id SERIAL PRIMARY KEY,
  user_id  BIGINT  NOT NULL UNIQUE,
  is_owner BOOLEAN NOT NULL DEFAULT FALSE
);

-- Provider Table --------------------------------------------------------------

CREATE TABLE access.providers
(
  provider_id SERIAL PRIMARY KEY,
  user_id     BIGINT      NOT NULL,
  dataset_id  VARCHAR(16) NOT NULL,
  is_manager  BOOLEAN     NOT NULL DEFAULT FALSE,

  CONSTRAINT provider_user_ds_uq UNIQUE (user_id, dataset_id)
);

-- Access Table ----------------------------------------------------------------

CREATE TABLE access.validdatasetuser
(
  user_id              BIGINT                   NOT NULL,
  dataset_id           VARCHAR(16)              NOT NULL,
  start_date           TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  duration             BIGINT                   NOT NULL DEFAULT -1,
  restriction_level_id SMALLINT                 NOT NULL
    REFERENCES access.restriction_level (restriction_level_id),
  purpose              VARCHAR                  NOT NULL,
  research_question    VARCHAR                  NOT NULL,
  analysis_plan        VARCHAR                  NOT NULL,
  dissemination_plan   VARCHAR                  NOT NULL,
  prior_auth           VARCHAR                  NOT NULL,
  approval_status_id   SMALLINT                 NOT NULL
    REFERENCES access.approval_status (approval_status_id),
  denial_reason        VARCHAR
);

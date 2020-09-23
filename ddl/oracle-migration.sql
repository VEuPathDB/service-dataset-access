-- studyaccess.approval_status

CREATE TABLE studyaccess.approval_status
(
  approval_status_id NUMBER(1) GENERATED AS IDENTITY
    CONSTRAINT approval_status_pk PRIMARY KEY,
  name               VARCHAR2(24) NOT NULL
);

INSERT INTO studyaccess.approval_status (name) VALUES ('approved');
INSERT INTO studyaccess.approval_status (name) VALUES ('requested');
INSERT INTO studyaccess.approval_status (name) VALUES ('denied');

GRANT SELECT ON studyaccess.approval_status TO COMM_WDK_W;

-- studyaccess.restriction_level

CREATE TABLE studyaccess.restriction_level
(
  restriction_level_id NUMBER(1) GENERATED AS IDENTITY
    CONSTRAINT restriction_level_pk PRIMARY KEY,
  name                 VARCHAR2(24) UNIQUE NOT NULL
);

INSERT INTO studyaccess.restriction_level (name) VALUES ('public');
INSERT INTO studyaccess.restriction_level (name) VALUES ('limited');
INSERT INTO studyaccess.restriction_level (name) VALUES ('protected');
INSERT INTO studyaccess.restriction_level (name) VALUES ('controlled');
INSERT INTO studyaccess.restriction_level (name) VALUES ('admin');

GRANT SELECT ON studyaccess.restriction_level TO COMM_WDK_W;

-- studyaccess.staff

CREATE TABLE studyaccess.staff
(
  staff_id NUMBER(8) GENERATED AS IDENTITY CONSTRAINT staff_pk PRIMARY KEY,
  user_id NUMBER(12) NOT NULL UNIQUE,
  is_owner NUMBER(1) DEFAULT 0 NOT NULL CHECK ( is_owner = 0 OR is_owner = 1 )
);

INSERT INTO studyaccess.staff (user_id, is_owner) VALUES (48, 1);
INSERT INTO studyaccess.staff (user_id, is_owner) VALUES (376, 1);
INSERT INTO studyaccess.staff (user_id, is_owner) VALUES (219825440, 1);
INSERT INTO studyaccess.staff (user_id, is_owner) VALUES (220902410, 1);
INSERT INTO studyaccess.staff (user_id, is_owner) VALUES (295652793, 1);
INSERT INTO studyaccess.staff (user_id, is_owner) VALUES (276765373, 1);

GRANT SELECT, INSERT, UPDATE, DELETE ON studyaccess.staff TO COMM_WDK_W;

-- studyaccess.providers

CREATE TABLE studyaccess.providers
(
  provider_id NUMBER(8) GENERATED AS IDENTITY
    CONSTRAINT providers_pk PRIMARY KEY,
  user_id NUMBER(12) NOT NULL,
  is_manager NUMBER(1) DEFAULT 0 NOT NULL
    CHECK ( is_manager = 0 OR is_manager = 1 ),
  dataset_id VARCHAR2(15) NOT NULL,
  CONSTRAINT provider_user_ds_uq UNIQUE (user_id, dataset_id)
);

GRANT SELECT, INSERT, UPDATE, DELETE ON studyaccess.providers TO COMM_WDK_W;

-- studyaccess.validdatasetuser

ALTER TABLE studyaccess.validdatasetuser
  ADD restriction_level_id NUMBER(1)
    REFERENCES studyaccess.restriction_level (restriction_level_id);
ALTER TABLE studyaccess.validdatasetuser
  ADD approval_status_id NUMBER(1)
    REFERENCES studyaccess.approval_status (approval_status_id);
ALTER TABLE studyaccess.validdatasetuser
  ADD date_denied TIMESTAMP;
ALTER TABLE studyaccess.validdatasetuser
  ADD allow_self_edits NUMBER(1) DEFAULT 0 NOT NULL;

UPDATE
  studyaccess.validdatasetuser v
SET
  restriction_level_id = (
    SELECT restriction_level_id
    FROM studyaccess.restriction_level
    WHERE name = v.restriction_level
  );
UPDATE
  studyaccess.validdatasetuser v
SET
  approval_status_id = (
    SELECT approval_status_id
    FROM studyaccess.approval_status
    WHERE
      name = (
      decode(approval_status,
         0, 'approved',
         1, 'requested',
         2, 'denied',
         'approved'
        )
      )
  );

ALTER TABLE studyaccess.validdatasetuser MODIFY (restriction_level_id NOT NULL);
ALTER TABLE studyaccess.validdatasetuser MODIFY (approval_status_id NOT NULL);
ALTER TABLE studyaccess.validdatasetuser DROP COLUMN restriction_level;
ALTER TABLE studyaccess.validdatasetuser DROP COLUMN approval_status;
ALTER TABLE studyaccess.validdatasetuser ADD denial_reason VARCHAR2(4000);

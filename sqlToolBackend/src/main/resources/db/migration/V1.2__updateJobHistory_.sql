Drop TABLE JOB_HISTORY;

CREATE TABLE JOB_HISTORY
(
    HISTORY_ID NUMBER (6)  NOT NULL ,
    EMPLOYEE_ID NUMBER (6)  NOT NULL ,
    START_DATE DATE  NOT NULL ,
    END_DATE DATE  NOT NULL ,
    JOB_ID VARCHAR2 (10 BYTE)  NOT NULL ,
    DEPARTMENT_ID NUMBER (4)
) LOGGING
;



ALTER TABLE JOB_HISTORY
    ADD CONSTRAINT JHIST_DATE_CHECK
        CHECK (end_date > start_date)
            INITIALLY IMMEDIATE
    ENABLE
        VALIDATE
;


COMMENT ON COLUMN JOB_HISTORY.END_DATE IS 'Last day of the employee in this job role. A not null column. Must be
greater than the start_date of the job_history table.
(enforced by constraint jhist_date_interval)'
;

COMMENT ON COLUMN JOB_HISTORY.JOB_ID IS 'Job role in which the employee worked in the past; foreign key to
job_id column in the jobs table. A not null column.'
;

COMMENT ON COLUMN JOB_HISTORY.DEPARTMENT_ID IS 'Department id in which the employee worked in the past; foreign key to deparment_id column in the departments table'
;

CREATE INDEX JHIST_JOB_IX ON JOB_HISTORY
    (
     JOB_ID ASC
    )
    LOGGING
    NOCOMPRESS
    NOPARALLEL
;

CREATE INDEX JHIST_EMP_IX ON JOB_HISTORY
    (
     EMPLOYEE_ID ASC
    )
    LOGGING
    NOCOMPRESS
    NOPARALLEL
;

CREATE INDEX JHIST_DEPT_IX ON JOB_HISTORY
    (
     DEPARTMENT_ID ASC
    )
    LOGGING
    NOCOMPRESS
    NOPARALLEL
;

CREATE UNIQUE INDEX JHIST_ID_DATE_PKX ON JOB_HISTORY
    (
     EMPLOYEE_ID ASC ,
     START_DATE ASC
    )
;

ALTER TABLE JOB_HISTORY
    ADD CONSTRAINT JHIST_ID_PK PRIMARY KEY ( HISTORY_ID ) ;



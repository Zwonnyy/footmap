-- Image path columns used by the application.
-- Store URL paths such as /uploads/team/{uuid}.png, not binary image data.

ALTER TABLE TEAM
    ADD COLUMN T_IMG VARCHAR(500) NULL;

ALTER TABLE FINFO
    ADD COLUMN F_IMG VARCHAR(500) NULL;

ALTER TABLE USER
    ADD COLUMN U_IMG VARCHAR(500) NULL;

ALTER TABLE BOARD
    ADD COLUMN B_IMG VARCHAR(500) NULL;


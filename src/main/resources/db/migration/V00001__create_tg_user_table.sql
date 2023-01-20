DROP TABLE IF EXISTS tg_user;
CREATE TABLE tg_user
(
    id               int8 NOT NULL,
    email            VARCHAR(255),
    first_login_date TIMESTAMP,
    first_name       VARCHAR(255),
    is_active        BOOLEAN,
    last_name        VARCHAR(255),
    user_name        VARCHAR(255),
    PRIMARY KEY (id)
);
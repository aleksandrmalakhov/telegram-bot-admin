DROP TABLE IF EXISTS tg_user;
CREATE TABLE tg_user
(
    id               int8    not null,
    first_login_date timestamp,
    first_name       varchar(255),
    last_name        varchar(255),
    user_name        varchar(255),
    is_active        boolean not null,
    account_id       int8,
    primary key (id)
);
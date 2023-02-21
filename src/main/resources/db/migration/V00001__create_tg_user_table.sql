DROP TABLE IF EXISTS tg_user;
create table tg_user
(
    id               int8    not null,
    first_login_date timestamp,
    first_name       varchar(255),
    is_active        boolean not null,
    last_name        varchar(255),
    user_name        varchar(255),
    account_id       int8,
    primary key (id)
)
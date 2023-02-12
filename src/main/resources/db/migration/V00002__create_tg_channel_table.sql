DROP TABLE IF EXISTS tg_channel;
CREATE TABLE tg_channel
(
    id          int8 not null,
    title       varchar(255),
    first_name  varchar(255),
    last_name   varchar(255),
    user_name   varchar(255),
    description varchar(255),
    primary key (id)
);
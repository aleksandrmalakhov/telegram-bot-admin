DROP TABLE IF EXISTS tg_channel;
create table tg_channel
(
    id          int8 not null,
    description varchar(255),
    first_name  varchar(255),
    last_name   varchar(255),
    title       varchar(255),
    user_name   varchar(255),
    primary key (id)
)
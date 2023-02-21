DROP TABLE IF EXISTS channel_subscribe;
create table channel_subscribe
(
    account_id        int8 not null,
    channel_id        int8 not null,
    date_create       timestamp,
    date_last_payment timestamp,
    date_next_payment timestamp,
    status            varchar(255),
    primary key (account_id, channel_id)
)
DROP TABLE IF EXISTS account_channel;
create table account_channel
(
    account_id int8 not null,
    channel_id int8 not null,
    primary key (account_id, channel_id)
)
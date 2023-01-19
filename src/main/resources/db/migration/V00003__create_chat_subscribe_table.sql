DROP TABLE IF EXISTS chat_subscribe;
CREATE TABLE chat_subscribe
(
    tg_chat int8 NOT NULL,
    tg_user int8 NOT NULL,
    role    VARCHAR(255),
    PRIMARY KEY (tg_chat, tg_user)
);
DROP TABLE IF EXISTS tg_chat;
CREATE TABLE tg_chat
(
    id          int8 NOT NULL,
    description VARCHAR(255),
    first_name  VARCHAR(255),
    last_name   VARCHAR(255),
    title       VARCHAR(255),
    type_chat   VARCHAR(255),
    user_name   VARCHAR(255),
    PRIMARY KEY (id)
);
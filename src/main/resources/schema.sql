DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users
(
    id       bigint       NOT NULL AUTO_INCREMENT,
    password varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

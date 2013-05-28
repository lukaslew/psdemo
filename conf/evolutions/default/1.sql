# Person schema

# --- !Ups

CREATE TABLE Person (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    gender INTEGER NOT NULL
);

# --- !Downs

DROP TABLE Person;
CREATE TABLE user (
    id varchar(36) NOT NULL,
    username text,
    email text,
    password text,
    PRIMARY KEY (id)
);

CREATE TABLE account (
    id varchar(36),
    userId varchar(36),
    account_name text,
    account_username text,
    account_password text,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES user(id) ON DELETE CASCADE
);
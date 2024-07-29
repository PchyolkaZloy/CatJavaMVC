create table if not exists cat_master
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    birth_date DATE         NOT NULL
);

create table if not exists cat
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    birth_date    DATE         NOT NULL,
    breed         VARCHAR(128),
    color         VARCHAR(128),
    cat_master_id INTEGER REFERENCES cat_master (id)
);

create table if not exists cat_cat_friends
(
    cat_id    INTEGER REFERENCES cat (id) ON DELETE CASCADE,
    friend_id INTEGER REFERENCES cat (id) ON DELETE CASCADE,
    PRIMARY KEY (cat_id, friend_id)
);

create table if not exists users
(
    id        SERIAL PRIMARY KEY,
    username  VARCHAR(64) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    user_role VARCHAR(64)  NOT NULL
);

create table if not exists user_cat_master
(
    user_id       INTEGER REFERENCES users (id) ON DELETE CASCADE,
    cat_master_id INTEGER REFERENCES cat_master (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, cat_master_id)
);


INSERT INTO cat_master (name, birth_date)
VALUES ('John', '1990-05-15'),
       ('Alice', '1985-10-20'),
       ('Bob', '1978-03-12');

INSERT INTO cat (name, birth_date, breed, color, cat_master_id)
VALUES ('Fluffy', '2019-07-01', 'Persian', 'White', 1),
       ('Whiskers', '2018-12-10', 'Siamese', 'Gray', 2),
       ('Mittens', '2020-02-25', 'Maine Coon', 'Brown', 1),
       ('Snowball', '2017-05-30', 'Scottish Fold', 'White', 3);

INSERT INTO cat_cat_friends (cat_id, friend_id)
VALUES (1, 3),
       (2, 4),
       (3, 1),
       (4, 2);

INSERT INTO users (username, password, user_role)
VALUES ('John123', '{bcrypt}$2a$12$Cb9AslqDEa09nsrTE7P0h.HKyd6ZI4ZsyVVbliB4N.LZbkjj4Kvbe', 'USER'),
       ('Alice239', '{bcrypt}$2a$12$7jOstWfoCtj1M0KkK8eL6eot7//8/m.TfwAHjCxBAZ0Bcuym5CfkG', 'USER'),
       ('BobKiller', '{bcrypt}$2a$12$erN/.YetbPqARxRhfouboeAy13N0CjSlmyjnsuVeOO8VZhqGSCJpy', 'USER'),
       ('sa', '{bcrypt}$2a$12$Ts1aFACxXxzthWh65dnd9eZNx4t9aNPCbWd55JM8lfcy6KDmp.JTW', 'ADMIN');

INSERT INTO user_cat_master (user_id, cat_master_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);
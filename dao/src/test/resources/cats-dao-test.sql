create table if not exists cat_master
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    birth_date DATE         NOT NULL
);
---SELECT SETVAL('cat_master_id_seq', (SELECT MAX(id) FROM cat_master));

create table if not exists cat
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    birth_date    DATE         NOT NULL,
    breed         VARCHAR(255),
    color         VARCHAR(255),
    cat_master_id INTEGER REFERENCES cat_master (id)
);
---SELECT SETVAL('cat_id_seq', (SELECT MAX(id) FROM cat));

create table if not exists cat_cat_friends
(
    cat_id    INTEGER REFERENCES cat (id) ON DELETE CASCADE,
    friend_id INTEGER REFERENCES cat (id) ON DELETE CASCADE,
    PRIMARY KEY (cat_id, friend_id)
);

INSERT INTO cat_master (id, name, birth_date)
VALUES (1, 'John', '1990-05-15'),
       (2, 'Alice', '1985-10-20'),
       (3, 'Bob', '1978-03-12');

INSERT INTO cat (id, name, birth_date, breed, color, cat_master_id)
VALUES (1, 'Fluffy', '2019-07-01', 'Persian', 'White', 1),
       (2, 'Whiskers', '2018-12-10', 'Siamese', 'Gray', 2),
       (3, 'Mittens', '2020-02-25', 'Maine Coon', 'Brown', 1),
       (4, 'Snowball', '2017-05-30', 'Scottish Fold', 'White', 3);

INSERT INTO cat_cat_friends (cat_id, friend_id)
VALUES (1, 3),
       (2, 4),
       (3, 1),
       (4, 2);
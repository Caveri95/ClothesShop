-- liquibase formatted sql

-- changeset Caveri:1

CREATE TABLE image
(
    image_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    data     oid NOT NULL
);



CREATE TABLE users
(
    user_id    INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email      VARCHAR(32)  NOT NULL UNIQUE,
    first_name VARCHAR(16)  NOT NULL,
    last_name  VARCHAR(16)  NOT NULL,
    password   VARCHAR(255) NOT NULL,
    phone      VARCHAR(15)  NOT NULL,
    role       VARCHAR(10) DEFAULT 'USER',
    image_id   INTEGER,

    CONSTRAINT phone_constraint CHECK (phone LIKE ('+7%')),
    FOREIGN KEY (image_id) REFERENCES image (image_id)
);

CREATE TABLE ad
(
    ad_id       INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    price       INTEGER      NOT NULL,
    title       VARCHAR(32)  NOT NULL,
    user_id     INTEGER      NOT NULL,
    image_id    INTEGER      NOT NULL,

    CONSTRAINT price_constraint CHECK ( price > 0 AND price < 10000000 ),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (image_id) REFERENCES image (image_id)
);

CREATE TABLE comment
(
    pk        INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created_at bigint      NOT NULL,
    text      VARCHAR(64) NOT NULL,
    ad_id     INTEGER     NOT NULL,
    user_id   INTEGER     NOT NULL,

    FOREIGN KEY (ad_id) REFERENCES ad (ad_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);
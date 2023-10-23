-- liquibase formatted sql

-- changeset Caveri:1

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
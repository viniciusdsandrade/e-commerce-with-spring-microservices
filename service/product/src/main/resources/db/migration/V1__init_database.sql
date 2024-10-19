CREATE TABLE IF NOT EXISTS category
(
    id          INTEGER      NOT NULL,
    description VARCHAR(255) NOT NULL,
    name        VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product
(
    id                 INTEGER          NOT NULL,
    description        VARCHAR(255)     NOT NULL,
    name               VARCHAR(255)     NOT NULL,
    available_quantity DOUBLE PRECISION NOT NULL,
    price              NUMERIC(38, 2)   NOT NULL,
    category_id        INTEGER          NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE SEQUENCE IF NOT EXISTS category_id_seq INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS product_id_seq INCREMENT BY 50;
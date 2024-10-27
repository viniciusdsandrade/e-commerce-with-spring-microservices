-- Remove o banco de dados se ele existir e cria um novo
DROP DATABASE IF EXISTS db_e_commerce_with_postgresql;
CREATE DATABASE  db_e_commerce_with_postgresql;
\c db_e_commerce_with_postgresql; -- Conecta ao novo banco

DROP TABLE IF EXISTS tb_notification;
DROP TABLE IF EXISTS tb_payment;
DROP TABLE IF EXISTS tb_order_line;
DROP TABLE IF EXISTS tb_category;
DROP TABLE IF EXISTS tb_order;
DROP TABLE IF EXISTS tb_product;
DROP TABLE IF EXISTS tb_customer;
DROP TABLE IF EXISTS tb_address;


-- Tabela de Endereço
CREATE TABLE IF NOT EXISTS tb_address
(
    id          BIGSERIAL    NOT NULL,
    street      VARCHAR(255) NOT NULL,
    houseNumber VARCHAR(10)  NOT NULL,
    zipCode     VARCHAR(20)  NOT NULL,

    PRIMARY KEY (id)
);

-- Tabela de Cliente
CREATE TABLE tb_customer
(
    id         BIGSERIAL    NOT NULL,
    firstname  VARCHAR(100) NOT NULL,
    lastname   VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NOT NULL,

    UNIQUE (email),
    PRIMARY KEY (id),

    address_id BIGINT       REFERENCES tb_address (id) ON DELETE SET NULL
);

-- Tabela de Produto
CREATE TABLE IF NOT EXISTS tb_product
(
    id                 BIGSERIAL      NOT NULL,
    name               VARCHAR(255)   NOT NULL,
    description        TEXT,
    available_quantity INT            NOT NULL,
    price              NUMERIC(10, 2) NOT NULL,
    created_at         TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id)
);

-- Trigger para atualizar o campo updated_at automaticamente
CREATE OR REPLACE FUNCTION update_timestamp()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_timestamp
    BEFORE UPDATE
    ON tb_product
    FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- Tabela de Pedido
CREATE TABLE IF NOT EXISTS tb_order
(
    id          BIGSERIAL   NOT NULL,
    orderDate   TIMESTAMPTZ NOT NULL,
    reference   VARCHAR(50) NOT NULL,

    PRIMARY KEY (id),

    customer_id BIGINT REFERENCES tb_customer (id) ON DELETE CASCADE
);

-- Tabela de Categoria
CREATE TABLE IF NOT EXISTS tb_category
(
    id          BIGSERIAL    NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description TEXT,

    PRIMARY KEY (id)
);

-- Tabela de Linha de Pedido
CREATE TABLE IF NOT EXISTS tb_order_line
(
    id         BIGSERIAL NOT NULL,
    quantity   INT       NOT NULL,

    PRIMARY KEY (id),

    product_id BIGINT REFERENCES tb_product (id) ON DELETE CASCADE,
    order_id   BIGINT REFERENCES tb_order (id) ON DELETE CASCADE
);

-- Tabela de Pagamento
CREATE TABLE IF NOT EXISTS tb_payment
(
    id        BIGSERIAL      NOT NULL,
    reference VARCHAR(50)    NOT NULL,
    amount    NUMERIC(10, 2) NOT NULL,
    status    VARCHAR(20)    NOT NULL,

    PRIMARY KEY (id),

    order_id  BIGINT REFERENCES tb_order (id) ON DELETE CASCADE
);

-- Tabela de Notificação
CREATE TABLE IF NOT EXISTS tb_notification
(
    id         BIGSERIAL    NOT NULL,
    sender     VARCHAR(100) NOT NULL,
    recipient  VARCHAR(100) NOT NULL,
    content    TEXT         NOT NULL,
    date       TIMESTAMPTZ  NOT NULL,

    PRIMARY KEY (id),

    payment_id BIGINT       REFERENCES tb_payment (id) ON DELETE SET NULL
);

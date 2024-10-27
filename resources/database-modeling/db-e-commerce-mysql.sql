DROP DATABASE IF EXISTS db_e_commerce_with_my_sql;
CREATE DATABASE IF NOT EXISTS db_e_commerce_with_my_sql;
USE db_e_commerce_with_my_sql;

-- Tabela de Endereço
CREATE TABLE IF NOT EXISTS tb_address
(
    id          BIGINT UNSIGNED AUTO_INCREMENT,
    street      VARCHAR(255) NOT NULL,
    houseNumber VARCHAR(10)  NOT NULL,
    zipCode     VARCHAR(20)  NOT NULL,

    PRIMARY KEY (id)
);

-- Tabela de Cliente
CREATE TABLE tb_customer
(
    id         BIGINT UNSIGNED AUTO_INCREMENT,
    firstname  VARCHAR(100) NOT NULL,
    lastname   VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NOT NULL UNIQUE,
    address_id BIGINT UNSIGNED,

    PRIMARY KEY (id),

    FOREIGN KEY (address_id) REFERENCES tb_address (id) ON DELETE SET NULL
);

-- Create table for product
CREATE TABLE IF NOT EXISTS tb_product
(
    id                 BIGINT UNSIGNED AUTO_INCREMENT,
    name               VARCHAR(255)   NOT NULL,
    description        TEXT,
    available_quantity INT UNSIGNED   NOT NULL,
    price              DECIMAL(10, 2) NOT NULL,

    PRIMARY KEY (id),

    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de Pedido
CREATE TABLE tb_order
(
    id          BIGINT UNSIGNED AUTO_INCREMENT,
    orderDate   DATETIME    NOT NULL,
    reference   VARCHAR(50) NOT NULL,
    customer_id BIGINT UNSIGNED,

    PRIMARY KEY (id),

    FOREIGN KEY (customer_id) REFERENCES tb_customer (id) ON DELETE CASCADE
);

-- Tabela de Categoria
CREATE TABLE IF NOT EXISTS tb_category
(
    id          BIGINT UNSIGNED AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    description TEXT,

    PRIMARY KEY (id)
);

-- Tabela de Linha de Pedido
CREATE TABLE IF NOT EXISTS tb_order_line
(
    id         BIGINT UNSIGNED AUTO_INCREMENT,
    quantity   INT UNSIGNED NOT NULL,
    product_id BIGINT UNSIGNED,
    order_id   BIGINT UNSIGNED,

    PRIMARY KEY (id),

    FOREIGN KEY (product_id) REFERENCES tb_product (id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES tb_order (id) ON DELETE CASCADE
);

-- Tabela de Pagamento
CREATE TABLE tb_payment
(
    id        BIGINT UNSIGNED AUTO_INCREMENT,
    reference VARCHAR(50)    NOT NULL,
    amount    DECIMAL(10, 2) NOT NULL,
    status    VARCHAR(20)    NOT NULL,
    order_id  BIGINT UNSIGNED,

    PRIMARY KEY (id),

    FOREIGN KEY (order_id) REFERENCES tb_order (id) ON DELETE CASCADE
);

-- Tabela de Notificação
CREATE TABLE tb_notification
(
    id         BIGINT UNSIGNED AUTO_INCREMENT,
    sender     VARCHAR(100) NOT NULL,
    recipient  VARCHAR(100) NOT NULL,
    content    TEXT         NOT NULL,
    date       DATETIME     NOT NULL,
    payment_id BIGINT UNSIGNED,

    PRIMARY KEY (id),

    FOREIGN KEY (payment_id) REFERENCES tb_payment (id) ON DELETE SET NULL
);
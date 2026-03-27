DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    age        INT          NOT NULL,
    address    VARCHAR(255) NOT NULL,
    married    BOOLEAN      NOT NULL
);

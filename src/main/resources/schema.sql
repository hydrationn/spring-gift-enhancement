create table product
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    price     BIGINT       NOT NULL,
    image_url VARCHAR(255)
);

CREATE TABLE member
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL
);

CREATE TABLE wish
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL
);

ALTER TABLE IF EXISTS wish
    ADD CONSTRAINT fk_wish_member
    FOREIGN KEY (member_id)
    REFERENCES member(id);

ALTER TABLE IF EXISTS wish
    ADD CONSTRAINT fk_wish_product
    FOREIGN KEY (product_id)
    REFERENCES product(id);

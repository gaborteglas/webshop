CREATE TABLE category (
id BIGINT AUTO_INCREMENT,
name VARCHAR(50) NOT NULL UNIQUE,
position_number BIGINT,
CONSTRAINT PK_category PRIMARY KEY(id)
);

INSERT INTO category
(name, position_number)
VALUES
("Egyéb", 1),
("Ismeretterjesztő", 2),
("Idegen nyelvű", 3),
("Világirodalom", 4),
("Magyar irodalom", 5);
("Gyermek és ijfúsági irodalom", 6);

CREATE TABLE category (
id BIGINT AUTO_INCREMENT,
name VARCHAR(50) NOT NULL UNIQUE,
position_number BIGINT,
CONSTRAINT PK_category PRIMARY KEY(id)
);

INSERT INTO category
(name, position_number)
VALUES
("Egyéb", 0),
("Ismeretterjesztő", 1),
("Regény", 2),
("Ezotéria", 3),
("Felnőtt", 4);

CREATE TABLE category (
id BIGINT AUTO_INCREMENT,
name VARCHAR(50) NOT NULL UNIQUE,
position_number BIGINT,
CONSTRAINT PK_category PRIMARY KEY(id)
);
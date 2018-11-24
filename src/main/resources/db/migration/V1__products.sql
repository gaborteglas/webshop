CREATE TABLE products (
 id bigint auto_increment,
 name VARCHAR(255) NOT NULL,
 address VARCHAR(255) NOT NULL UNIQUE,
 producer VARCHAR(255) NOT NULL,
 price bigint NOT NULL,
 status VARCHAR(50) NOT NULL,
 CONSTRAINT pk_products PRIMARY KEY(id)
 );
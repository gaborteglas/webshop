create table products (id bigint auto_increment,name varchar(255) NOT NULL, address varchar(255) NOT NULL unique,
 producer varchar(255) NOT NULL,price bigint NOT NULL,deleted varchar(50) NOT NULL,constraint pk_products PRIMARY KEY(id));

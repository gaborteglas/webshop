CREATE TABLE basket (
id BIGINT auto_increment,
user_name varchar(255) NOT NULL,
product_id bigint NOT NULL,
quantity BIGINT NOT NULL
CONSTRAINT pk_basket PRIMARY KEY(id),
CONSTRAINT fk_products FOREIGN KEY(products.id),
CONSTRAINT fk_users FOREIGN KEY(users.user_name)
);
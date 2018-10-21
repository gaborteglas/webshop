CREATE TABLE basket (
id BIGINT auto_increment,
user_name varchar(255) NOT NULL,
product_id bigint NOT NULL,
quantity BIGINT NOT NULL,
CONSTRAINT pk_basket PRIMARY KEY(id),
FOREIGN KEY (product_id) REFERENCES products(id),
FOREIGN KEY (user_name) REFERENCES users(user_name)
);
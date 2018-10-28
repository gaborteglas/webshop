CREATE TABLE orders (
id BIGINT auto_increment,
user_id BIGINT,
date DATETIME NOT NULL,
status VARCHAR(50) NOT NULL,
quantity BIGINT,
price BIGINT,
CONSTRAINT pk_order PRIMARY KEY(id),
FOREIGN KEY(user_id) REFERENCES Users(id)
);
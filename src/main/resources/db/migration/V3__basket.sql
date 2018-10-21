CREATE TABLE Basket (
id BIGINT AUTO_INCREMENT,
user_id BIGINT,
product_id BIGINT,
CONSTRAINT PK_Basket PRIMARY KEY(id),
FK_Basket FOREIGN KEY(user_id) REFERENCES Users(id),
FK_Basket FOREIGN KEY(product_id) REFERENCES Products(id)
);


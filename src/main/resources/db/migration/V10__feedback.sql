CREATE TABLE feedback (
id BIGINT AUTO_INCREMENT,
rating_text VARCHAR(255),
rating_score TINYINT NOT NULL,
product_id BIGINT,
user_id BIGINT,
CONSTRAINT PK_feedback PRIMARY KEY(id),
FOREIGN KEY(product_id) REFERENCES products(id),
FOREIGN KEY(user_id) REFERENCES users(id)
);
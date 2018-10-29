ALTER TABLE products
ADD COLUMN category_id BIGINT,
ADD FOREIGN KEY (category_id) REFERENCES category(id);
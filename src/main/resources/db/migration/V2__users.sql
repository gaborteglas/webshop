CREATE TABLE users (
 id bigint auto_increment,
 user_name VARCHAR(255) NOT NULL UNIQUE,
 full_name VARCHAR(255) NOT NULL,
 password VARCHAR(255) NOT NULL,
 status VARCHAR(50) NOT NULL,
 CONSTRAINT pk_users PRIMARY KEY(id)
 );

 INSERT INTO users
(user_name, full_name, password, status)
VALUES("testuser", "Test User", "123", "CUSTOMER"),
("testadmin", "Test Admin", "456", "ADMIN");
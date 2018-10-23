CREATE TABLE products (
 id bigint auto_increment,
 name VARCHAR(255) NOT NULL,
 address VARCHAR(255) NOT NULL UNIQUE,
 producer VARCHAR(255) NOT NULL,
 price bigint NOT NULL,
 status VARCHAR(50) NOT NULL,
 CONSTRAINT pk_products PRIMARY KEY(id)
 );


INSERT INTO products
(name, address, producer, price, status)
VALUES ('Az aliceblue 50 árnyalata', 'az-aliceblue-50-arnyalata', 'E. L. Doe', 9999, 'ACTIVE'),
('Legendás programozók és megfigyelésük', 'legendas-programozok-es-megfigyelesuk','J. K. Doe',  3999, 'ACTIVE'),
('Az 50 első Trainer osztály', 'az-50-elso-trainer-osztaly', 'Jack Doe', 5999, 'ACTIVE'),
('Hogyan neveld a junior fejlesztődet', 'hogyan-neveld-a-junior-fejlesztodet', 'Jane Doe', 6499, 'ACTIVE'),
('Junior most és mindörökké', 'junior-most-es-mindorokke', 'James Doe', 2999, 'ACTIVE'),
('A Java ura: A classok szövetsége', 'a-java-ura:-a-classok-szovetsege', 'J.R.R. Doe', 2899, 'ACTIVE'),
('Junior fejlesztő falinaptár 2019', 'junior-fejleszto-falinaptar-2019', 'Peter Doe', 4699, 'ACTIVE'),
('Junioroskert', 'junioroskert', 'Anton Doe', 5599, 'ACTIVE'),
('Nemzeti Java', 'nemzeti-java', 'Sándor Doe', 3799, 'ACTIVE'),
('A junior csillagok', 'a-junior-csillagok', 'Géza Doe', 4899, 'ACTIVE'),
('Egy kis Stackoverflow', 'egy-kis-stackoverflow', 'Darcey Doe', 3999, 'ACTIVE'),
('A hengermalomi bárdok', 'a-hengermalomi-bardok', 'János Doe', 8399, 'ACTIVE'),
('Juniorsoron', 'juniorsoron', 'Stephen Doe', 2999, 'ACTIVE'),
('80 nap alatt a Java körül', '80-nap-alatt-a-java-korul', 'Jules Doe', 6099, 'ACTIVE'),
('Junior a szénakazalban', 'junior-a-szenakazalban', 'Ken Doe', 499, 'ACTIVE');
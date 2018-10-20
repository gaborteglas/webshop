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
VALUES ('Az aliceblue 50 árnyalata', 'aliceblue', 'E. L. Doe', 9999, 'active'),
('Legendás programozók és megfigyelésük', 'legendas','J. K. Doe',  3999, 'active'),
('Az 50 első Trainer osztály', 'trainer', 'Jack Doe', 5999, 'active'),
('Hogyan neveld a junior fejlesztődet', 'junior', 'Jane Doe', 6499, 'active'),
('Junior most és mindörökké', 'mindorokke', 'James Doe', 2999, 'active'),
('A Java ura: A classok szövetsége', 'szovetseg', 'J.R.R. Doe', 2899, 'active'),
('Junior fejlesztő falinaptár 2019', 'naptar', 'Peter Doe', 4699, 'active'),
('Junioroskert', 'kert', 'Anton Doe', 5599, 'active'),
('Nemzeti Java', 'nemzeti', 'Sándor Doe', 3799, 'active'),
('A junior csillagok', 'csillagok', 'Géza Doe', 4899, 'active'),
('Egy kis Stackoverflow', 'stackoverflow', 'Darcey Doe', 3999, 'active'),
('A hengermalomi bárdok', 'hengermalom', 'János Doe', 8399, 'active'),
('Juniorsoron', 'juniorsor', 'Stephen Doe', 2999, 'active'),
('80 nap alatt a Java körül', 'javakorul', 'Jules Doe', 6099, 'active'),
('Junior a szénakazalban', 'szenakazal', 'Ken Doe', 499, 'active');
ALTER TABLE products
ADD COLUMN category_id BIGINT DEFAULT 1,
ADD FOREIGN KEY (category_id) REFERENCES category(id);

INSERT INTO products
(name, address, producer, price, status, category_id)
VALUES ('A szürke 50 árnyalata', 'a-szurke-50-arnyalata', 'E. L. James', 3999, 'ACTIVE', 5),
('Junior fejlesztő falinaptár 2019', 'junior-fejleszto-falinaptar-2019', 'Kia Doe', 4699, 'ACTIVE', 1),
('Legendás állatok és megfigyelésük', 'legendas-allatok-es-megfigyelesuk','J. K. Rowling',  3999, 'ACTIVE', 2),
('Így neveld a sárkányodat', 'igy-neveld-a-sarkanyodat', 'Cressida Cowell', 2499, 'ACTIVE', 4),
('A Gyűrűk ura: A gyűrű szövetsége', 'a-gyuruk-ura-a-gyuru-szovetsege', 'J.R.R. Tolkien', 2899, 'ACTIVE', 2),
('Cseresznyéskert', 'cseresznyeskert', 'Anton Csehov', 5599, 'ACTIVE', 4),
('Nemzeti Dal', 'nemzeti-dal', 'Petőfi Sándor', 3799, 'ACTIVE', 2),
('Az egri csillagok', 'az-egri-csillagok', 'Gárdonyi Géza', 4899, 'ACTIVE', 3),
('Egy kis szívesség', 'egy-kis-szivesseg', 'Darcey Bell', 3999, 'ACTIVE', 5),
('A walesi bárdok', 'a-walesi-bardok', 'Arany János', 1399, 'ACTIVE', 2),
('80 nap alatt a Föld körül', '80-nap-alatt-a-fold-korul', 'Jules Verne', 2099, 'ACTIVE', 2),
('Most és mindörökké', 'most-es-mindorokke', 'James Jones', 999, 'ACTIVE', 3);
ALTER TABLE products
ADD COLUMN category_id BIGINT DEFAULT 1,
ADD FOREIGN KEY (category_id) REFERENCES category(id);

INSERT INTO products
(name, address, producer, price, status, category_id)
VALUES
('A szürke 50 árnyalata', 'a-szurke-50-arnyalata', 'E. L. James', 3390, 'ACTIVE', 4),
('Junior fejlesztő falinaptár 2019', 'junior-fejleszto-falinaptar-2019', 'Kia Doe', 4990, 'ACTIVE', 1),
('OCA: SE 8 Programmer Study Guide', 'oca-se-8-programmer-study-guide','Jeanne Boyarsky & Scott Selikoff',  14990, 'ACTIVE', 2),
('Megúszós kaják', 'meguszos-kajak', 'Fördős Zé', 5090, 'ACTIVE', 2),
('The Fellowship of the Ring', 'the-fellowship-of-the-ring', 'J.R.R. Tolkien', 3990, 'ACTIVE', 3),
('Cseresznyéskert', 'cseresznyeskert', 'Anton Pavlovics Csehov', 340, 'ACTIVE', 4),
('Harry Potter and the Philosopher''s Stone', 'harry-potter-and-the-philisophers-stone', 'J.K.Rowling', 4990, 'ACTIVE', 3),
('A japán rejtély', 'a-japan-rejtely', 'Muraközy László', 4690, 'ACTIVE', 2),
('Aranyhaj és a nagy gubanc', 'aranyhaj-es-a-nagy-gubanc', 'Disney klasszikusok', 2490, 'ACTIVE', 6),
('A walesi bárdok', 'a-walesi-bardok', 'Arany János', 1390, 'ACTIVE', 5),
('Addig se iszik', 'addig-se-iszik', 'Bödőcs Tibor', 2990, 'ACTIVE', 5),
('Hygge', 'hygge', 'Meik Wiking', 4490, 'ACTIVE', 2);
('A kis herceg', 'a-kis-herceg', 'Antoine de Saint-Exupéry', 2990, 'ACTIVE', 6),
('Trónok harca', 'tronok-harca', 'George R.R.Martin', 4990, 'ACTIVE', 4),
('Amit a falkától tanultam', 'amit-a-falkatol-tanultam', 'Cesar Millan', 3990, 'ACTIVE', 2),
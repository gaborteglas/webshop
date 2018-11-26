INSERT INTO orders
(user_id, date, status, delivery_address)
VALUES
(4, '2018-04-20','ACTIVE', '1239 Budapest, Fakopács utca 5/a'),
(4, '2018-06-10','ACTIVE', '1055 Budapest, Szent István körút 10'),
(4, '2018-07-04','ACTIVE', '1117 Budapest, Budafoki út 56'),
(5, '2018-07-04','ACTIVE', '1117 Budapest, Budafoki út 56'),
(4, '2018-11-21','ACTIVE', '1239 Budapest, Fakopács utca 5/a');

INSERT INTO orderitem
(order_id, product_id, product_price, quantity)
VALUES
(1, 8, 4690, 3),
(2, 11, 2990, 1),
(2, 8, 4690, 1),
(2, 13, 2990, 1),
(2, 15, 3990, 1),
(2, 9, 2490, 1),
(2, 1, 3390, 1),
(2, 10, 1390, 1),
(2, 6, 340, 1),
(2, 7, 4490, 1),
(2, 12, 4990, 1),
(2, 4, 5090, 1),
(2, 3, 14990, 1),
(2, 5, 3990, 1),
(2, 14, 4990, 1),
(3, 3, 14990, 1),
(4, 4, 5090, 1),
(4, 5, 3990, 1),
(4, 2, 4990, 1);


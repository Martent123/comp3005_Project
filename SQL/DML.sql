use comp3005;

#default roles
INSERT INTO role (role) 
VALUES ('owner');

INSERT INTO role (role) 
VALUES ('user');

#default user for testing
INSERT INTO user_t (name,email,phone,role) 
VALUES ('TestOwner','test@gmail.com','123-321-1234','owner');

INSERT INTO user_t (name,email,phone,role) 
VALUES ('TestUser','test@gmail.com','333-444-6666','user');

INSERT INTO user_t (name,email,phone,role) 
VALUES ('TestUser1','test@gmail.com','333-444-6666','user');


#insert some fake order for user
INSERT INTO order_t (tracking,user_id) 
VALUES ('On The Way', (select id from user_t where name = 'TestUser'));
INSERT INTO order_t (tracking,user_id) 
VALUES ('Delivered', (select id from user_t where name = 'TestUser'));

#create a store for owner
INSERT INTO store (store_name,owner_id) 
VALUES ('Store One', (select id from user_t where name = 'TestOwner'));
INSERT INTO store (store_name,owner_id) 
VALUES ('Store Two', (select id from user_t where name = 'TestOwner'));

#create fake store address
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('2333', 'Store Street', 'K1S 5L5','billing');
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('2333', 'Store Street', 'K1S 5L5','shipping'); 

INSERT INTO store_address (address_id,store_id) 
VALUES ((select id from address where street_num = '2333' and address_type = 'shipping'),(select id from store where store_name = 'Store One'));
INSERT INTO store_address (address_id,store_id) 
VALUES ((select id from address where street_num = '2333' and address_type = 'billing'),(select id from store where store_name = 'Store One'));

#create fake user address
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('321', 'User Street', 'K1S 5L5','billing');
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('321', 'User Street', 'K1S 5L5','shipping'); 

INSERT INTO user_address (address_id,user_id) 
VALUES ((select id from address where street_num = '321' and address_type = 'shipping'),(select id from user_t where name = 'TestUser'));
INSERT INTO user_address (address_id,user_id) 
VALUES ((select id from address where street_num = '321' and address_type = 'billing'),(select id from user_t where name = 'TestUser'));

#create fake owner address
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('3213', 'Owner Street', 'K1S 5L5','billing');
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('3213', 'Owner Street', 'K1S 5L5','shipping'); 

INSERT INTO user_address (address_id,user_id) 
VALUES ((select id from address where street_num = '3213' and address_type = 'shipping'),(select id from user_t where name = 'TestOwner'));
INSERT INTO user_address (address_id,user_id) 
VALUES ((select id from address where street_num = '3213' and address_type = 'billing'),(select id from user_t where name = 'TestOwner'));

#warehouse
INSERT INTO warehouse (name,phone) 
VALUES ('warehouse1', '123-321-1111');
INSERT INTO warehouse (name,phone) 
VALUES ('warehouse2', '123-321-2222');
INSERT INTO warehouse (name,phone) 
VALUES ('warehouse3', '123-321-3333');

#create fake warehouse address
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('1111', 'warehouse1 Street', 'K1S 5L5','billing');
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('1111', 'warehouse1 Street', 'K1S 5L5','shipping'); 
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('2222', 'warehouse2 Street', 'K1S 5L5','billing');
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('2222', 'warehouse2 Street', 'K1S 5L5','shipping'); 
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('3333', 'warehouse3 Street', 'K1S 5L5','billing');
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('3333', 'warehouse3 Street', 'K1S 5L5','shipping'); 

INSERT INTO warehouse_address (address_id,warehouse_id) 
VALUES ((select id from address where street_num = '1111' and address_type = 'billing'),(select id from warehouse where name = 'warehouse1'));
INSERT INTO warehouse_address (address_id,warehouse_id) 
VALUES ((select id from address where street_num = '1111' and address_type = 'shipping'),(select id from warehouse where name = 'warehouse1'));
INSERT INTO warehouse_address (address_id,warehouse_id) 
VALUES ((select id from address where street_num = '2222' and address_type = 'billing'),(select id from warehouse where name = 'warehouse2'));
INSERT INTO warehouse_address (address_id,warehouse_id) 
VALUES ((select id from address where street_num = '2222' and address_type = 'shipping'),(select id from warehouse where name = 'warehouse2'));
INSERT INTO warehouse_address (address_id,warehouse_id) 
VALUES ((select id from address where street_num = '3333' and address_type = 'billing'),(select id from warehouse where name = 'warehouse3'));
INSERT INTO warehouse_address (address_id,warehouse_id) 
VALUES ((select id from address where street_num = '3333' and address_type = 'shipping'),(select id from warehouse where name = 'warehouse3'));

#publisher
INSERT INTO publisher (name,email,phone,bank_account) 
VALUES ('publisher1','publisher1@gmail.com', '123-111-1111','publisher1BankAccount');
INSERT INTO publisher (name,email,phone,bank_account) 
VALUES ('publisher2','publisher2@gmail.com', '123-111-2222','publisher2BankAccount');
INSERT INTO publisher (name,email,phone,bank_account) 
VALUES ('publisher3','publisher3@gmail.com', '123-111-3333','publisher3BankAccount');

#create fake publisher address
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('p1111', 'publisher1 Street', 'K1S 5L5','billing');
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('p1111', 'publisher1 Street', 'K1S 5L5','shipping'); 
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('p2222', 'publisher2 Street', 'K1S 5L5','billing');
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('p2222', 'publisher2 Street', 'K1S 5L5','shipping'); 
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('p3333', 'publisher3 Street', 'K1S 5L5','billing');
INSERT INTO address (street_num,street_name,postal_code,address_type) 
VALUES ('p3333', 'publisher3 Street', 'K1S 5L5','shipping'); 

INSERT INTO publisher_address (address_id,publisher_id) 
VALUES ((select id from address where street_num = 'p1111' and address_type = 'billing'),(select id from publisher where name = 'publisher1'));
INSERT INTO publisher_address (address_id,publisher_id) 
VALUES ((select id from address where street_num = 'p1111' and address_type = 'shipping'),(select id from publisher where name = 'publisher1'));
INSERT INTO publisher_address (address_id,publisher_id) 
VALUES ((select id from address where street_num = 'p2222' and address_type = 'billing'),(select id from publisher where name = 'publisher2'));
INSERT INTO publisher_address (address_id,publisher_id) 
VALUES ((select id from address where street_num = 'p2222' and address_type = 'shipping'),(select id from publisher where name = 'publisher2'));
INSERT INTO publisher_address (address_id,publisher_id) 
VALUES ((select id from address where street_num = 'p3333' and address_type = 'billing'),(select id from publisher where name = 'publisher3'));
INSERT INTO publisher_address (address_id,publisher_id) 
VALUES ((select id from address where street_num = 'p3333' and address_type = 'shipping'),(select id from publisher where name = 'publisher3'));

#create genre
INSERT INTO genre (name) 
VALUES ('genre1');
INSERT INTO genre (name) 
VALUES ('genre2');
INSERT INTO genre (name) 
VALUES ('genre3');

#create authors
INSERT INTO author (name) 
VALUES ('author1');
INSERT INTO author (name) 
VALUES ('author2');
INSERT INTO author (name) 
VALUES ('author3');

#create books
INSERT INTO book (name,ISBN,price,genre_id,nop,publisher_id) 
VALUES ('book1','ISBN 1 100000001',10.99, (select id from genre where name = 'genre1'),387,(select id from publisher where name = 'publisher1'));

INSERT INTO book (name,ISBN,price,genre_id,nop,publisher_id) 
VALUES ('book2','ISBN 2 100000002',20, (select id from genre where name = 'genre2'),222,(select id from publisher where name = 'publisher1'));

INSERT INTO book (name,ISBN,price,genre_id,nop,publisher_id) 
VALUES ('book3','ISBN 3 100000003',25.00, (select id from genre where name = 'genre3'),111,(select id from publisher where name = 'publisher1'));

INSERT INTO book (name,ISBN,price,genre_id,nop,publisher_id) 
VALUES ('book4','ISBN 4 100000004',7.99, (select id from genre where name = 'genre1'),555,(select id from publisher where name = 'publisher2'));

INSERT INTO book (name,ISBN,price,genre_id,nop,publisher_id) 
VALUES ('book5','ISBN 1 100000005',8.99, (select id from genre where name = 'genre1'),332,(select id from publisher where name = 'publisher3'));

INSERT INTO book (name,ISBN,price,genre_id,nop,publisher_id) 
VALUES ('book6','ISBN 6 100000006',200, (select id from genre where name = 'genre1'),123,(select id from publisher where name = 'publisher1'));

INSERT INTO book (name,ISBN,price,genre_id,nop,publisher_id) 
VALUES ('book7','ISBN 7 100000007',300, (select id from genre where name = 'genre3'),321,(select id from publisher where name = 'publisher2'));

INSERT INTO book (name,ISBN,price,genre_id,nop,publisher_id) 
VALUES ('book8','ISBN 8 100000008',400, (select id from genre where name = 'genre1'),22,(select id from publisher where name = 'publisher3'));

INSERT INTO book (name,ISBN,price,genre_id,nop,publisher_id) 
VALUES ('book9','ISBN 9 100000009',50, (select id from genre where name = 'genre2'),11,(select id from publisher where name = 'publisher3'));

INSERT INTO book (name,ISBN,price,genre_id,nop,publisher_id) 
VALUES ('book10','ISBN 10 100000010',50, (select id from genre where name = 'genre2'),11,(select id from publisher where name = 'publisher3'));

#relation between books and warehouse.
INSERT INTO book_warehouse (warehouse_id,book_id,quantity) 
VALUES ((select id from warehouse where name = 'warehouse1'), (select id from book where name = 'book1'),101);
INSERT INTO book_warehouse (warehouse_id,book_id,quantity) 
VALUES ((select id from warehouse where name = 'warehouse1'), (select id from book where name = 'book2'),102);
INSERT INTO book_warehouse (warehouse_id,book_id,quantity) 
VALUES ((select id from warehouse where name = 'warehouse1'), (select id from book where name = 'book3'),100);
INSERT INTO book_warehouse (warehouse_id,book_id,quantity) 
VALUES ((select id from warehouse where name = 'warehouse1'), (select id from book where name = 'book4'),100);
INSERT INTO book_warehouse (warehouse_id,book_id,quantity) 
VALUES ((select id from warehouse where name = 'warehouse1'), (select id from book where name = 'book5'),130);
INSERT INTO book_warehouse (warehouse_id,book_id,quantity) 
VALUES ((select id from warehouse where name = 'warehouse1'), (select id from book where name = 'book6'),4);
INSERT INTO book_warehouse (warehouse_id,book_id,quantity) 
VALUES ((select id from warehouse where name = 'warehouse1'), (select id from book where name = 'book7'),5);
INSERT INTO book_warehouse (warehouse_id,book_id,quantity) 
VALUES ((select id from warehouse where name = 'warehouse1'), (select id from book where name = 'book8'),7);
INSERT INTO book_warehouse (warehouse_id,book_id,quantity) 
VALUES ((select id from warehouse where name = 'warehouse1'), (select id from book where name = 'book9'),11);
INSERT INTO book_warehouse (warehouse_id,book_id,quantity) 
VALUES ((select id from warehouse where name = 'warehouse2'), (select id from book where name = 'book1'),100);
INSERT INTO book_warehouse (warehouse_id,book_id,quantity) 
VALUES ((select id from warehouse where name = 'warehouse3'), (select id from book where name = 'book2'),10);
INSERT INTO book_warehouse (warehouse_id,book_id,quantity) 
VALUES ((select id from warehouse where name = 'warehouse3'), (select id from book where name = 'book10'),11);

#relation between books and order.
INSERT INTO order_book (order_id,book_id,quantity) 
VALUES ((select id from order_t where tracking = 'Delivered'), (select id from book where name = 'book1'),10);

INSERT INTO order_book (order_id,book_id,quantity) 
VALUES ((select id from order_t where tracking = 'Delivered'), (select id from book where name = 'book2'),6);

INSERT INTO order_book (order_id,book_id,quantity) 
VALUES ((select id from order_t where tracking = 'Delivered'), (select id from book where name = 'book3'),5);

INSERT INTO order_book (order_id,book_id,quantity) 
VALUES ((select id from order_t where tracking = 'Delivered'), (select id from book where name = 'book4'),3);

INSERT INTO order_book (order_id,book_id,quantity) 
VALUES ((select id from order_t where tracking = 'Delivered'), (select id from book where name = 'book5'),1);

INSERT INTO order_book (order_id,book_id,quantity) 
VALUES ((select id from order_t where tracking = 'On The Way'), (select id from book where name = 'book5'),3);

INSERT INTO order_book (order_id,book_id,quantity) 
VALUES ((select id from order_t where tracking = 'On The Way'), (select id from book where name = 'book6'),2);

INSERT INTO order_book (order_id,book_id,quantity) 
VALUES ((select id from order_t where tracking = 'On The Way'), (select id from book where name = 'book7'),1);

INSERT INTO order_book (order_id,book_id,quantity) 
VALUES ((select id from order_t where tracking = 'On The Way'), (select id from book where name = 'book8'),5);

INSERT INTO order_book (order_id,book_id,quantity) 
VALUES ((select id from order_t where tracking = 'On The Way'), (select id from book where name = 'book9'),2);

#relation between books and author.
INSERT INTO book_author (author_id,book_id) 
VALUES ((select id from author where name = 'author1'), (select id from book where name = 'book1'));

INSERT INTO book_author (author_id,book_id) 
VALUES ((select id from author where name = 'author2'), (select id from book where name = 'book1'));

INSERT INTO book_author (author_id,book_id) 
VALUES ((select id from author where name = 'author2'), (select id from book where name = 'book2'));

INSERT INTO book_author (author_id,book_id) 
VALUES ((select id from author where name = 'author3'), (select id from book where name = 'book3'));

INSERT INTO book_author (author_id,book_id) 
VALUES ((select id from author where name = 'author1'), (select id from book where name = 'book4'));
INSERT INTO book_author (author_id,book_id) 
VALUES ((select id from author where name = 'author2'), (select id from book where name = 'book5'));
INSERT INTO book_author (author_id,book_id) 
VALUES ((select id from author where name = 'author3'), (select id from book where name = 'book6'));
INSERT INTO book_author (author_id,book_id) 
VALUES ((select id from author where name = 'author1'), (select id from book where name = 'book7'));
INSERT INTO book_author (author_id,book_id) 
VALUES ((select id from author where name = 'author2'), (select id from book where name = 'book8'));
INSERT INTO book_author (author_id,book_id) 
VALUES ((select id from author where name = 'author2'), (select id from book where name = 'book9'));
INSERT INTO book_author (author_id,book_id) 
VALUES ((select id from author where name = 'author3'), (select id from book where name = 'book9'));
INSERT INTO book_author (author_id,book_id) 
VALUES ((select id from author where name = 'author2'), (select id from book where name = 'book10'));


INSERT INTO store_book (store_id,book_id) 
VALUES ((select id from Store where store_name = 'Store One'), (select id from book where name = 'book1'));
INSERT INTO store_book (store_id,book_id) 
VALUES ((select id from Store where store_name = 'Store One'), (select id from book where name = 'book2'));
INSERT INTO store_book (store_id,book_id) 
VALUES ((select id from Store where store_name = 'Store One'), (select id from book where name = 'book3'));
INSERT INTO store_book (store_id,book_id) 
VALUES ((select id from Store where store_name = 'Store One'), (select id from book where name = 'book4'));
INSERT INTO store_book (store_id,book_id) 
VALUES ((select id from Store where store_name = 'Store One'), (select id from book where name = 'book5'));
INSERT INTO store_book (store_id,book_id) 
VALUES ((select id from Store where store_name = 'Store One'), (select id from book where name = 'book6'));
INSERT INTO store_book (store_id,book_id) 
VALUES ((select id from Store where store_name = 'Store One'), (select id from book where name = 'book7'));
INSERT INTO store_book (store_id,book_id) 
VALUES ((select id from Store where store_name = 'Store One'), (select id from book where name = 'book8'));
INSERT INTO store_book (store_id,book_id) 
VALUES ((select id from Store where store_name = 'Store One'), (select id from book where name = 'book9'));
INSERT INTO store_book (store_id,book_id) 
VALUES ((select id from Store where store_name = 'Store Two'), (select id from book where name = 'book10'));



#find the role and address of user
select * from user_t;

#get the address of given user
select * from address 
join user_address on address.id = user_address.address_id
join user_t on user_address.user_id = user_t.id
where user_t.id = 1;

#update the user table info
update user_t
set email = "", phone = ""
where id = 1;

#get the general info of booklist
select book.id,book.name,book.price,book.ISBN,genre.name as genre,book.nop,book.publisher_id,publisher.name as publisher_name, sum(book_warehouse.quantity) as stock from book 
join genre on book.genre_id = genre.id
join publisher on book.publisher_id = publisher.id
join store_book on book.id = store_book.book_id
join store on store_book.store_id = store.id
join book_warehouse on book.id = book_warehouse.book_id
where store.store_name = "Store One" and book.id =9
group by book_warehouse.book_id;

#find author by id
select author.id,author.name from author
join book_author on author.id = book_author.author_id
where book_author.book_id = 1;

#make new order
INSERT INTO order_t (tracking,user_id) 
VALUES ('order received', 1);
INSERT INTO order_book(order_id,book_id,quantity)
VALUES (4,2,4);

#find the book by genre
select book.id,book.name,book.price,book.ISBN,genre.name as genre,book.nop,book.publisher_id,publisher.name as publisher_name, sum(book_warehouse.quantity) as stock from book 
join genre on book.genre_id = genre.id
join publisher on book.publisher_id = publisher.id
join store_book on book.id = store_book.book_id
join store on store_book.store_id = store.id
join book_warehouse on book.id = book_warehouse.book_id
where store.store_name = "Store One" and genre.name ="genre2"
group by book_warehouse.book_id;

#search
select book.id,book.name,book.price,book.ISBN,genre.name as genre,book.nop,book.publisher_id,publisher.name as publisher_name,author.name as author_name, sum(book_warehouse.quantity) as stock from book 
join genre on book.genre_id = genre.id
join publisher on book.publisher_id = publisher.id
join store_book on book.id = store_book.book_id
join store on store_book.store_id = store.id
join book_warehouse on book.id = book_warehouse.book_id
join book_author on book.id = book_author.book_id
join author on book_author.author_id = author.id
where store.store_name = "Store One" and (book.name LIKE "%abc%" OR book.ISBN LIKE "%book%" OR genre.name LIKE "%book%" OR author.name LIKE "%author1%")
group by book_warehouse.book_id;

#get the general info of booklist not in the store
select book.id,book.name,book.price,book.ISBN,genre.name as genre,book.nop,book.publisher_id,publisher.name as publisher_name, sum(book_warehouse.quantity) as stock from book 
join genre on book.genre_id = genre.id
join publisher on book.publisher_id = publisher.id
join book_warehouse on book.id = book_warehouse.book_id
where book.id not in (select store_book.book_id from store_book 
join store on store_book.store_id = store.id
where store.store_name = "Store One")
group by book_warehouse.book_id;

select store_book.book_id from store_book 
join store on store_book.store_id = store.id
where store.store_name = "Store One";
#clear the book in current store
DELETE FROM store_book 
WHERE store_id = (select id from store where store.store_name = "Store One");
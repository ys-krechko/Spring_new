CREATE TABLE IF NOT EXISTS ROLE (
ID BIGINT auto_increment,
NAME VARCHAR(50),
PRIMARY KEY (ID)
);

INSERT INTO ROLE (ID, NAME) VALUES (1, 'ROLE_USER');
INSERT INTO ROLE (ID, NAME) VALUES (2, 'ROLE_ADMIN');

CREATE TABLE IF NOT EXISTS USERS (
ID BIGINT auto_increment,
NAME VARCHAR(50),
PASSWORD VARCHAR(100),
PRIMARY KEY (ID),
);

INSERT INTO USERS (ID, NAME, PASSWORD) VALUES (1, 'user.simple','$2a$10$ZZ49JJylsBY8v8dZL/95FuNQgDZeo9oiEgzL.0iyCxUUCjSsCPiAO');
INSERT INTO USERS (ID, NAME, PASSWORD) VALUES (2, 'user.admin','$2a$10$l/D6AGt8vYJG.cW/lIT44uy.TAYkV9UYJ8bPuGKBwuva/ERc9Ct4K');

CREATE TABLE IF NOT EXISTS USER_ROLE (
USER_ID BIGINT,
ROLE_ID BIGINT,
FOREIGN KEY (USER_ID) REFERENCES USERS(ID),
FOREIGN KEY (ROLE_ID) REFERENCES ROLE(ID)
);

INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES (1, 1);
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES (2, 2);

CREATE TABLE IF NOT EXISTS Customer (
id BIGINT AUTO_INCREMENT,
customers_first_name VARCHAR(50),
customers_last_name VARCHAR(50),
customers_passport_number VARCHAR(50),
customers_contract_number VARCHAR(50),
customers_contract_date_of_signing DATE,
PRIMARY KEY (id)
);

INSERT INTO Customer (id, customers_first_name, customers_last_name, customers_passport_number, customers_contract_number, customers_contract_date_of_signing) VALUES (1, 'John', 'Snow', 'KN2049600', '524/ds-a9s0d', '2018-12-21');
INSERT INTO Customer (id, customers_first_name, customers_last_name, customers_passport_number, customers_contract_number, customers_contract_date_of_signing) VALUES (2, 'Jane', 'Doy', 'KL4568408', '65-0d156/12-1', '2016-09-27');

CREATE TABLE IF NOT EXISTS Insurance (
id BIGINT AUTO_INCREMENT,
insurance_type VARCHAR(50),
insurance_price DOUBLE,
PRIMARY KEY (id)
);

INSERT INTO Insurance (id, insurance_type, insurance_price) VALUES (1, 'Premium', 251.00);
INSERT INTO Insurance (id, insurance_type, insurance_price) VALUES (2, 'Regular', 99.99);

CREATE TABLE IF NOT EXISTS Hotels_Address (
id BIGINT AUTO_INCREMENT,
post_code VARCHAR(50),
country VARCHAR(50),
city VARCHAR(50),
street VARCHAR(50),
building_number VARCHAR(10),
PRIMARY KEY (id)
);

INSERT INTO Hotels_Address (id, post_code, country, city, street, building_number) VALUES (1, '235540', 'Egypt', 'Hurgada', 'some', '1');
INSERT INTO Hotels_Address (id, post_code, country, city, street, building_number) VALUES (2, 'NW1/W1', 'England', 'London', 'Baker Street', '221B');

CREATE TABLE IF NOT EXISTS Hotel (
id BIGINT AUTO_INCREMENT,
hotels_name VARCHAR(50),
stars INT,
hotels_address_id BIGINT,
PRIMARY KEY (id),
FOREIGN KEY (hotels_address_id) REFERENCES Hotels_Address (id)
);

INSERT INTO Hotel (id, hotels_name, stars, hotels_address_id) VALUES (1, 'Azure', 4, 1);
INSERT INTO Hotel (id, hotels_name, stars, hotels_address_id) VALUES (2, 'SH', 5, 2);

CREATE TABLE IF NOT EXISTS Hotel_Room_Price (
id BIGINT AUTO_INCREMENT,
date_of_month DATE,
price_per_night DOUBLE,
PRIMARY KEY (id)
);

INSERT INTO Hotel_Room_Price (id, date_of_month, price_per_night) VALUES (1, '2019-01-01', 2.00);
INSERT INTO Hotel_Room_Price (id, date_of_month, price_per_night) VALUES (2, '2019-02-01', 2.10);
INSERT INTO Hotel_Room_Price (id, date_of_month, price_per_night) VALUES (3, '2019-03-01', 2.20);
INSERT INTO Hotel_Room_Price (id, date_of_month, price_per_night) VALUES (4, '2019-04-01', 2.30);
INSERT INTO Hotel_Room_Price (id, date_of_month, price_per_night) VALUES (5, '2019-05-01', 2.40);
INSERT INTO Hotel_Room_Price (id, date_of_month, price_per_night) VALUES (6, '2019-01-06', 2.50);
INSERT INTO Hotel_Room_Price (id, date_of_month, price_per_night) VALUES (7, '2019-07-01', 2.60);
INSERT INTO Hotel_Room_Price (id, date_of_month, price_per_night) VALUES (8, '2019-08-01', 2.70);
INSERT INTO Hotel_Room_Price (id, date_of_month, price_per_night) VALUES (9, '2019-09-01', 2.80);
INSERT INTO Hotel_Room_Price (id, date_of_month, price_per_night) VALUES (10, '2019-10-01', 2.90);
INSERT INTO Hotel_Room_Price (id, date_of_month, price_per_night) VALUES (11, '2019-11-01', 3.00);
INSERT INTO Hotel_Room_Price (id, date_of_month, price_per_night) VALUES (12, '2019-12-01', 3.10);
INSERT INTO Hotel_Room_Price (id, date_of_month, price_per_night) VALUES (13, '2019-01-01', 3.00);
INSERT INTO Hotel_Room_Price (id, date_of_month, price_per_night) VALUES (14, '2019-02-01', 3.40);
INSERT INTO Hotel_Room_Price (id, date_of_month, price_per_night) VALUES (15, '2019-03-01', 4.00);

CREATE TABLE IF NOT EXISTS Hotel_Room (
id BIGINT AUTO_INCREMENT,
type VARCHAR(50),
number_of_guests INT,
food_type VARCHAR(50),
PRIMARY KEY (id)
);

INSERT INTO Hotel_Room (id, type, number_of_guests, food_type) VALUES (1, 'regular', 2, 'not all inclusive');
INSERT INTO Hotel_Room (id, type, number_of_guests, food_type) VALUES (2, 'luxury', 1, 'all inclusive');

CREATE TABLE IF NOT EXISTS Hotel_room_Hotel_room_price (
hotel_room_id BIGINT,
hotel_room_price_id BIGINT,
FOREIGN KEY (hotel_room_id) REFERENCES Hotel_Room (id),
FOREIGN KEY (hotel_room_price_id) REFERENCES Hotel_Room_Price (id)
);

INSERT INTO Hotel_room_Hotel_room_price (hotel_room_id, hotel_room_price_id) VALUES (1, 1);
INSERT INTO Hotel_room_Hotel_room_price (hotel_room_id, hotel_room_price_id) VALUES (1, 2);
INSERT INTO Hotel_room_Hotel_room_price (hotel_room_id, hotel_room_price_id) VALUES (1, 3);
INSERT INTO Hotel_room_Hotel_room_price (hotel_room_id, hotel_room_price_id) VALUES (1, 4);
INSERT INTO Hotel_room_Hotel_room_price (hotel_room_id, hotel_room_price_id) VALUES (1, 5);
INSERT INTO Hotel_room_Hotel_room_price (hotel_room_id, hotel_room_price_id) VALUES (1, 6);
INSERT INTO Hotel_room_Hotel_room_price (hotel_room_id, hotel_room_price_id) VALUES (1, 7);
INSERT INTO Hotel_room_Hotel_room_price (hotel_room_id, hotel_room_price_id) VALUES (1, 8);
INSERT INTO Hotel_room_Hotel_room_price (hotel_room_id, hotel_room_price_id) VALUES (1, 9);
INSERT INTO Hotel_room_Hotel_room_price (hotel_room_id, hotel_room_price_id) VALUES (1, 10);
INSERT INTO Hotel_room_Hotel_room_price (hotel_room_id, hotel_room_price_id) VALUES (1, 11);
INSERT INTO Hotel_room_Hotel_room_price (hotel_room_id, hotel_room_price_id) VALUES (1, 12);
INSERT INTO Hotel_room_Hotel_room_price (hotel_room_id, hotel_room_price_id) VALUES (2, 13);
INSERT INTO Hotel_room_Hotel_room_price (hotel_room_id, hotel_room_price_id) VALUES (2, 14);
INSERT INTO Hotel_room_Hotel_room_price (hotel_room_id, hotel_room_price_id) VALUES (2, 15);

CREATE TABLE IF NOT EXISTS Hotel_Room_Hotel (
id BIGINT AUTO_INCREMENT,
hotel_id BIGINT,
hotel_room_id BIGINT,
PRIMARY KEY (id),
FOREIGN KEY (hotel_room_id) REFERENCES Hotel_Room (id),
FOREIGN KEY (hotel_id) REFERENCES Hotel (id)
);

INSERT INTO Hotel_Room_Hotel (id, hotel_id, hotel_room_id) VALUES (1, 1, 1);
INSERT INTO Hotel_Room_Hotel (id, hotel_id, hotel_room_id) VALUES (2, 2, 2);
INSERT INTO Hotel_Room_Hotel (id, hotel_id, hotel_room_id) VALUES (3, 2, 1);

CREATE TABLE IF NOT EXISTS Order_List (
id BIGINT AUTO_INCREMENT,
beginning_date_of_tour DATE,
amount_of_days_of_tour INT,
number_of_tourists INT,
total_price DOUBLE,
user_id BIGINT,
insurance_id BIGINT,
customer_id BIGINT,
hotel_room_hotel_id BIGINT,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES "USERS" (ID),
FOREIGN KEY (customer_id) REFERENCES Customer (id),
FOREIGN KEY (insurance_id) REFERENCES Insurance (id),
FOREIGN KEY (hotel_room_hotel_id) REFERENCES Hotel_Room_Hotel (id)
);

INSERT INTO Order_List (id, beginning_date_of_tour, amount_of_days_of_tour, number_of_tourists, total_price, user_id, insurance_id, customer_id, hotel_room_hotel_id) VALUES (1, '2020-03-15', 10, 2, 999.99, 1, 1, 1, 1);

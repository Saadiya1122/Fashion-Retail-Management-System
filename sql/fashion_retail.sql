CREATE DATABASE fashion_retail;
USE fashion_retail;

SELECT DATABASE();
CREATE TABLE PRODUCT (
	product_id BIGINT PRIMARY KEY,
    product_name VARCHAR(1000),
    sku VARCHAR(100) UNIQUE,
    price DECIMAL(10,2),
    discount DECIMAL(5,2),
    no_of_sales BIGINT,
    no_of_reviews BIGINT,
    seller_location VARCHAR(255),
    product_link VARCHAR(1000)
    );
 
DESCRIBE PRODUCT;

-- Change this path below to the location of the cleaned CSV file
LOAD DATA LOCAL INFILE '/Users/saadiyashaikh/Desktop/Advance Programming/Fashion-Retail-Management-System/data/daraz_cleaned_products.csv'
INTO TABLE PRODUCT
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(product_id, sku, product_name, price, discount, no_of_sales, no_of_reviews, seller_location, product_link);

SELECT COUNT(*) AS total_products FROM PRODUCT;
SELECT * FROM PRODUCT LIMIT 20;

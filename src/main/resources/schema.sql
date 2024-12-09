CREATE TABLE IF NOT EXISTS transaction (
   id SERIAL PRIMARY KEY,
   date VARCHAR(10) NOT NULL,
   recipient VARCHAR(100) NOT NULL,
   price NUMERIC(20,2) NOT NULL,
   has_bill BOOLEAN NOT NULL

);

CREATE TABLE IF NOT EXISTS product (
   id SERIAL PRIMARY KEY,
   name VARCHAR(100) NOT NULL,
   price NUMERIC(7,2) NOT NULL,
   quantity NUMERIC(7,3) NOT NULL,
   full_price NUMERIC(7,2) NOT NULL,
   category_id INT NOT NULL,
   transaction_id INT NOT NULL,
   FOREIGN KEY (transaction_id) REFERENCES transaction(id),
   FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
)
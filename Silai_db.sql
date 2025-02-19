CREATE DATABASE silai_db;
USE silai_db;
CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255),
    address TEXT
);
CREATE TABLE measurements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    chest FLOAT,
    waist FLOAT,
    inseam FLOAT,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('Pending', 'In Progress', 'Completed', 'Delivered'),
    delivery_date TIMESTAMP,
    total_price DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE DATABASE silai_db;
USE silai_db;

-- Customers Table
CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE,
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Measurements Table (Customer-Specific)
CREATE TABLE measurements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    chest FLOAT,
    waist FLOAT,
    inseam FLOAT,
    sleeve FLOAT,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

-- Orders Table (Links to Customers)
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('Pending', 'In Progress', 'Completed', 'Delivered') DEFAULT 'Pending',
    delivery_date TIMESTAMP NULL,
    total_price DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

-- Order Items Table (For multiple garments in one order)
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    garment_type VARCHAR(100),  -- Example: Shirt, Pants, Suit
    fabric VARCHAR(100),
    quantity INT DEFAULT 1,
    price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- Invoices Table
CREATE TABLE invoices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    invoice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10,2),
    payment_status ENUM('Pending', 'Paid', 'Cancelled') DEFAULT 'Pending',
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- Inventory Table (For managing fabrics & accessories)
CREATE TABLE inventory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(255) NOT NULL,
    category ENUM('Fabric', 'Thread', 'Button', 'Zipper') NOT NULL,
    quantity INT DEFAULT 0,
    unit_price DECIMAL(10,2),
    supplier_id INT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE SET NULL
);

-- Suppliers Table (For fabric vendors)
CREATE TABLE suppliers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(255) UNIQUE,
    address TEXT
);

-- Users Table (For role-based access)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('Admin', 'Tailor', 'Manager') DEFAULT 'Tailor'
);

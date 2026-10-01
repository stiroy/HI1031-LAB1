-- Active: 1790872155429@@localhost@9000@postgres
CREATE TABLE T_products (
 id SERIAL PRIMARY KEY,
 name VARCHAR(100) NOT NULL,
 description VARCHAR(100) NOT NULL DEFAULT 'none',
 category VARCHAR(100) NOT NULL,
 quantity INT NOT NULL DEFAULT 0,
 price DOUBLE PRECISION NOT NULL DEFAULT 0
);


CREATE TABLE T_customers (
    customerID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE T_customerOrders (
 orderID INT NOT NULL,
 customerID INT NOT NULL
);

CREATE TABLE T_order (
 
 itemID INT NOT NULL,
 quantity INT NOT NULL 
);

CREATE TABLE T_employee (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL, -- BCrypt or Argon2 id hash
    user_type VARCHAR(20) NOT NULL,     
    employee_id VARCHAR(50) UNIQUE,      
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
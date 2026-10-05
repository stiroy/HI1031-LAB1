-- Active: 1790872155429@@localhost@9000@postgres
-- PRODUCT TABLE --
CREATE TABLE T_products (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL DEFAULT 'none',
    category VARCHAR(100) NOT NULL,
    quantity INT NOT NULL DEFAULT 0 CHECK (quantity >= 0),
    price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE T_products (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL DEFAULT 'none',
    category VARCHAR(100) NOT NULL,
    quantity INT NOT NULL DEFAULT 0 CHECK (quantity >= 0),
    price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_category 
        FOREIGN KEY (category) 
        REFERENCES T_categories(category_name) 
        ON UPDATE CASCADE
);

CREATE TABLE T_categories (
    category_name PRIMARY KEY
);

-- USER and roles
CREATE TABLE T_users (
    username VARCHAR(100) PRIMARY KEY,
    password_hash VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE T_roles (
    role_name VARCHAR(20) PRIMARY KEY
);

INSERT INTO T_roles(role_name)
VALUES
    ('CUSTOMER'),
    ('EMPLOYEE'),
    ('ADMIN');
-- Only one role per username
CREATE TABLE T_user_roles (
    username VARCHAR(100) UNIQUE NOT NULL,
    role_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (username, role_name),
    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (username)
        REFERENCES T_users(username)
        ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role
        FOREIGN KEY (role_name)
        REFERENCES T_roles(role_name)
);




CREATE TABLE T_orders (
    order_id VARCHAR(20) PRIMARY KEY,
    customer_username VARCHAR(100) NOT NULL,
    order_status VARCHAR(20) NOT NULL DEFAULT 'PENDING'
        CHECK (order_status IN ('PENDING', 'PACKED')),
    total_price DECIMAL(10,2) DEFAULT 0,
    packed_by VARCHAR(100),
    packed_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE
        DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_customer
        FOREIGN KEY (customer_username)
        REFERENCES T_users(username),
    CONSTRAINT fk_order_employee
        FOREIGN KEY (packed_by)
        REFERENCES T_users(username)
);




CREATE TABLE T_order_product (
    order_product_id SERIAL PRIMARY KEY,
    order_id VARCHAR(20) NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_item_order
        FOREIGN KEY (order_id)
        REFERENCES T_orders(order_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_item_product
        FOREIGN KEY (product_id)
        REFERENCES T_products(product_id)
);

CREATE VIEW V_employee_orders AS SELECT o.order_id, o.customer_username, p.name AS product_name, oi.quantity, oi.unit_price, o.order_status, o.total_price, o.packed_by, o.packed_at, o.created_at FROM T_orders o JOIN T_order_product oi ON o.order_id = oi.order_id JOIN T_products p ON oi.product_id = p.product_id;

  
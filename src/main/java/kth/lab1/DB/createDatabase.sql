-- Active: 1790872155429@@localhost@9000@postgres
-- PRODUCT TABLE --
CREATE TABLE T_products (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL DEFAULT 'none',
    category VARCHAR(100) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);


-- USER and roles
CREATE TABLE T_users (
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(100) UNIQUE NOT NULL,
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

CREATE TABLE T_user_roles (
    username VARCHAR(100) NOT NULL,
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
    order_id SERIAL PRIMARY KEY,
    customer_id UUID NOT NULL,
    order_status VARCHAR(20) NOT NULL DEFAULT 'NEW'
        CHECK (
            order_status IN (
                'UNPACKED',
                'PACKED'
            )
        ),

    total_price DECIMAL(10,2) DEFAULT 0,
    packed_by UUID,
    packed_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_customer
        FOREIGN KEY (customer_id)
        REFERENCES T_customers(customer_id),
    CONSTRAINT fk_order_employee
        FOREIGN KEY (packed_by)
        REFERENCES T_employees(employee_id)
);


CREATE TABLE T_order_items (
    order_item_id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_item_order
        FOREIGN KEY (order_id)
        REFERENCES T_orders(order_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_item_product
        FOREIGN KEY (product_id)
        REFERENCES T_products(product_id)
);


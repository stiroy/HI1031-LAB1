-- Active: 1790872155429@@localhost@9000@postgres
CREATE TABLE T_products (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL DEFAULT 'none',
    category VARCHAR(100) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP

);

CREATE TABLE T_customers (
    customer_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE T_employees (
    employee_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('EMPLOYEE', 'ADMIN')),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE T_orders (
    order_id SERIAL PRIMARY KEY,
    customer_id UUID NOT NULL,
    order_status VARCHAR(20) NOT NULL DEFAULT 'NEW'
        CHECK (
            order_status IN (
                'NEW',
                'PACKING',
                'PACKED',
                'SHIPPED',
                'DELIVERED',
                'CANCELLED'
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


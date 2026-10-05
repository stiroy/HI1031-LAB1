INSERT INTO T_categories (category_name) 
VALUES 
    ('Drink'),
    ('Cutlery'),
    ('Electronics'),
    ('Office'),
    ('Home')
ON CONFLICT (category_name) DO NOTHING;

INSERT INTO T_products (name, description, category, quantity, price)
VALUES
    ('Laptop', 'fett bra', 'Electronics', 14, 299.99),
    ('Glass of water', 'mmm water', 'Drink', 100, 100.00),
    ('Spoon', 'A silver spoon', 'Cutlery', 50, 9.99),
    ('Ergonomic Chair', 'Adjustable mesh office chair with lumbar support', 'Office', 12, 189.50),
    ('Mechanical Keyboard', 'RGB backlit mechanical keyboard with blue switches', 'Electronics', 25, 79.99),
    ('Fork', 'Stainless steel dinner fork', 'Cutlery', 150, 4.50),
    ('Butter Knife', 'Blunt knife designed for butter and spreads', 'Cutlery', 80, 5.25),
    ('Sparkling Water', 'Refreshing carbonated mineral water 500ml', 'Drink', 200, 2.50),
    ('Coffee Mug', 'Ceramic 350ml mug, dishwasher safe', 'Home', 45, 12.00),
    ('Wireless Mouse', '2.4GHz optical wireless mouse with USB receiver', 'Electronics', 35, 24.99);

INSERT INTO T_users(username, password_hash)
VALUES ('exampleUser', 'hashed_password');

INSERT INTO T_user_roles(username, role_name)
VALUES ('exampleUser', 'EMPLOYEE');

  SELECT * FROM V_employee_orders;
  SELECT * FROM T_products;

INSERT INTO T_users (username, password_hash) VALUES ('testuser', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f');

CREATE VIEW V_user_roles AS
SELECT u.username, u.is_active, ur.role_name FROM T_users u JOIN T_user_roles ur ON u.username = ur.username;

-- dummy users password is 123 :-)
INSERT INTO T_users (username, password_hash, is_active)
VALUES
  ('admin', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', true),
  ('employee', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', true),
  ('user', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', true);

INSERT INTO T_user_roles (username, role_name)
VALUES 
  ('admin', 'ADMIN'),
  ('employee', 'EMPLOYEE'),
  ('user', 'CUSTOMER');

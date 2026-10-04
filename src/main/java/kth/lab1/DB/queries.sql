INSERT INTO T_products (
    name,
    description,
    category,
    quantity,
    price
)
VALUES
('Laptop', 'fett bra', 'Computers', 14, 299.99),
('Glass of water', 'mmm water', 'Neccesities', 100, 100),
('Spoon', 'A silver spoon', 'Cutlerry', 50, 9.99);

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

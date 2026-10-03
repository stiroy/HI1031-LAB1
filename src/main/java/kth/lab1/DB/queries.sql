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
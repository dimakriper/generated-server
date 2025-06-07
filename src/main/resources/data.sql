-- Initial category data
INSERT INTO categories (name, color) VALUES
( 'Food', '#FF5733'),
( 'Transport', '#33FF57'),
( 'Entertainment', '#3357FF'),
( 'Utilities', '#F3FF33'),
( 'Housing', '#FF33F3');

-- Initial transaction data
INSERT INTO transactions (description, amount, date, type, category_id) VALUES
( 'Grocery shopping', 50.75, CURRENT_DATE, 'EXPENSE', 1),
( 'Bus ticket', 2.50, CURRENT_DATE, 'EXPENSE', 2),
( 'Salary', 2000.00, CURRENT_DATE, 'INCOME', NULL);

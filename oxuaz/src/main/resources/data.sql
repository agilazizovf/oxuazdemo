-- Insert into users
INSERT INTO users (email, password) VALUES
                                        ('admin1@gmail.com', '$2a$12$RTNq7JGW68FJDTsc3bGxmeyQueGFqAOh5NByss/JyvCglxKYZQ6zi');

-- Insert roles
INSERT INTO roles(name, admin)
VALUES
    ('ROLE_ADMIN', 1);

-- Assign roles automatically based on user type
INSERT INTO user_roles (user_id, role_id)
select 1,id from roles where admin=1;

-- INSERT INTO user_roles (user_id, role_id)
-- select 2,id from roles where client=1;

INSERT INTO categories (name) VALUES
                                      ('Technology'),
                                      ('Health'),
                                      ('Education'),
                                      ('Travel'),
                                      ('Business');
INSERT INTO posts (title, description, url, created_at, updated_at, category_id) VALUES
                                                                                         ('Top Java Libraries in 2025', 'Explore the most popular Java libraries developers are using in 2025.', 'file1.pdf', '2025-05-20 10:30:00', '2025-05-20 10:30:00', 1),
                                                                                         ('10 Health Tips for Remote Workers', 'Stay healthy while working from home with these proven tips.', 'file2.pdf', '2025-05-21 09:00:00', '2025-05-21 09:00:00', 2),
                                                                                         ('How to Ace Online Learning', 'A guide to success in remote and hybrid education.', NULL, '2025-05-22 14:15:00', '2025-05-22 14:15:00', 3),
                                                                                         ('Best Travel Apps of the Year', 'These apps will make your trips smoother and more enjoyable.', 'image1.png', '2025-05-23 08:00:00', '2025-05-23 08:00:00', 4),
                                                                                         ('Startup Trends to Watch', 'A look at emerging business models in 2025.', NULL, '2025-05-24 11:45:00', '2025-05-24 11:45:00', 5);

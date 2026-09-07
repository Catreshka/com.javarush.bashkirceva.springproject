-- Пользователи
INSERT INTO users (username, email, password, role) VALUES
('john_doe', 'john@example.com', '$2a$10$6He.0toB8vcOitKaQ4YeUu1UM61nTL0tHUn.UArQLpLeFVhAsiHi2', 'USER'),
('admin', 'admin@example.com', '$2a$10$XSNs1JXGudUcArQfZaUxF.KlGPSIFvHWSG.VU8meQ9vggwmJBvXZ2', 'ADMIN');

-- Задачи
INSERT INTO tasks (title, description, deadline, status, user_id) VALUES
('Complete homework', 'Finish math and science homework', '2023-12-01', 'PENDING', 1),
('Fix server', 'Resolve critical issue on production server', '2023-11-25', 'IN_PROGRESS', 2);

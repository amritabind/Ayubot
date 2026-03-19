-- AyuBot Database Setup Script
-- This script creates demo users with properly hashed passwords

USE ayubot;

-- Clear existing demo users (if any)
DELETE FROM users WHERE email IN ('patient@ayubot.com', 'doctor@ayubot.com');

-- Insert demo users with BCrypt hashed password: "123"
-- Hash: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhkO
INSERT INTO users (name, email, password, role) VALUES 
('Demo Patient', 'patient@ayubot.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhkO', 'PATIENT'),
('Demo Doctor', 'doctor@ayubot.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhkO', 'DOCTOR');

SELECT 'Demo users created successfully!' as message;
SELECT id, name, email, role FROM users;

CREATE DATABASE IF NOT EXISTS ayubot;
USE ayubot;

DROP TABLE IF EXISTS medical_reports;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  role ENUM('PATIENT','DOCTOR') NOT NULL,
  assigned_doctor_id BIGINT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (assigned_doctor_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE medical_reports (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  patient_id BIGINT NOT NULL,
  report_type VARCHAR(50) NOT NULL,
  report_name VARCHAR(255) NOT NULL,
  extracted_text TEXT,
  ai_analysis TEXT,
  file_data LONGTEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (patient_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE consultations (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  patient_id BIGINT NOT NULL,
  doctor_id BIGINT,
  symptoms TEXT NOT NULL,
  affected_area VARCHAR(100),
  ai_diagnosis TEXT,
  doctor_notes TEXT,
  prescription TEXT,
  status ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED') DEFAULT 'PENDING',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (patient_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (doctor_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE appointments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  patient_id BIGINT NOT NULL,
  doctor_id BIGINT NOT NULL,
  appointment_date DATETIME NOT NULL,
  appointment_type VARCHAR(100) NOT NULL,
  reason TEXT,
  status ENUM('SCHEDULED', 'COMPLETED', 'CANCELLED') DEFAULT 'SCHEDULED',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (patient_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (doctor_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE patient_profiles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL UNIQUE,
  date_of_birth DATE,
  gender VARCHAR(20),
  phone_number VARCHAR(20),
  address TEXT,
  blood_group VARCHAR(10),
  height DECIMAL(5,2),
  weight DECIMAL(5,2),
  chronic_conditions TEXT,
  current_medications TEXT,
  medical_history TEXT,
  smoking_status VARCHAR(50),
  alcohol_consumption VARCHAR(50),
  exercise_frequency VARCHAR(50),
  emergency_contact_name VARCHAR(100),
  emergency_contact_phone VARCHAR(20),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Demo accounts with BCrypt hashed passwords (password: "123")
-- BCrypt hash for "123": $2a$10$xHqTZ9EqLkXjLQvF8L8YTOqLPqPZQ0qF0mQXvQZWQHXqQZWQHXqQZ
-- Note: When the Spring Boot app runs, it will auto-create the table based on JPA entity
-- You can insert demo users after the app creates the table, or let the app handle it

-- To manually insert demo users with pre-hashed passwords (optional):
-- INSERT INTO users (name, email, password, role) VALUES 
-- ('Demo Patient','patient@ayubot.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhkO','PATIENT'),
-- ('Demo Doctor','doctor@ayubot.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhkO','DOCTOR');
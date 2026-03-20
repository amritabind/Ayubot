-- AyuBot Database Schema for PostgreSQL
-- This script creates all necessary tables

-- Drop existing tables if they exist (for fresh start)
DROP TABLE IF EXISTS report_share CASCADE;
DROP TABLE IF EXISTS medical_reports CASCADE;
DROP TABLE IF EXISTS consultation CASCADE;
DROP TABLE IF EXISTS appointment CASCADE;
DROP TABLE IF EXISTS patient_profile CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    assigned_doctor_id BIGINT,
    assigned_admin_id BIGINT,
    specialty VARCHAR(255),
    license_image LONGTEXT,
    bio TEXT,
    qualifications VARCHAR(255),
    clinic_address TEXT,
    phone_number VARCHAR(20),
    verified BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_assigned_doctor FOREIGN KEY (assigned_doctor_id) REFERENCES users(id) ON DELETE SET NULL,
    CONSTRAINT fk_assigned_admin FOREIGN KEY (assigned_admin_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Create patient_profile table
CREATE TABLE patient_profile (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    age INTEGER,
    gender VARCHAR(20),
    blood_group VARCHAR(10),
    medical_history TEXT,
    allergies VARCHAR(500),
    current_medications VARCHAR(500),
    chronic_diseases VARCHAR(255),
    height DECIMAL(5, 2),
    weight DECIMAL(5, 2),
    bmi DECIMAL(5, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_patient_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create medical_reports table
CREATE TABLE medical_reports (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    report_type VARCHAR(100),
    report_name VARCHAR(255),
    extracted_text LONGTEXT,
    ai_analysis LONGTEXT,
    file_data LONGTEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_report_patient FOREIGN KEY (patient_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create consultation table
CREATE TABLE consultation (
    id BIGSERIAL PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    symptoms VARCHAR(500),
    affected_area VARCHAR(255),
    ai_diagnosis TEXT,
    status VARCHAR(50) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_consultation_doctor FOREIGN KEY (doctor_id) REFERENCES users(id) ON DELETE SET NULL,
    CONSTRAINT fk_consultation_patient FOREIGN KEY (patient_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create appointment table
CREATE TABLE appointment (
    id BIGSERIAL PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    appointment_date TIMESTAMP,
    status VARCHAR(50) DEFAULT 'PENDING',
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES users(id) ON DELETE SET NULL,
    CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create report_share table
CREATE TABLE report_share (
    id BIGSERIAL PRIMARY KEY,
    report_id BIGINT NOT NULL,
    shared_by BIGINT NOT NULL,
    shared_with BIGINT NOT NULL,
    shared_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_report_share_report FOREIGN KEY (report_id) REFERENCES medical_reports(id) ON DELETE CASCADE,
    CONSTRAINT fk_report_share_by FOREIGN KEY (shared_by) REFERENCES users(id) ON DELETE SET NULL,
    CONSTRAINT fk_report_share_with FOREIGN KEY (shared_with) REFERENCES users(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_patient_profile_user_id ON patient_profile(user_id);
CREATE INDEX idx_medical_reports_patient_id ON medical_reports(patient_id);
CREATE INDEX idx_consultation_patient_id ON consultation(patient_id);
CREATE INDEX idx_consultation_doctor_id ON consultation(doctor_id);
CREATE INDEX idx_appointment_patient_id ON appointment(patient_id);
CREATE INDEX idx_appointment_doctor_id ON appointment(doctor_id);

-- Insert test users
-- Password: 'password' (BCrypt hashed)
-- Hash: $2a$10$slYQmyNdGzin/eexlxAitch8.P7n.P7Q0H5WK5bAD8OqZLqPOC.g2

INSERT INTO users (name, email, password, role, verified, created_at, updated_at) 
VALUES ('Test Patient', 'patient@test.com', '$2a$10$slYQmyNdGzin/eexlxAitch8.P7n.P7Q0H5WK5bAD8OqZLqPOC.g2', 'PATIENT', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (name, email, password, role, specialty, verified, created_at, updated_at) 
VALUES ('Test Doctor', 'doctor@test.com', '$2a$10$slYQmyNdGzin/eexlxAitch8.P7n.P7Q0H5WK5bAD8OqZLqPOC.g2', 'DOCTOR', 'General Medicine', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (name, email, password, role, verified, created_at, updated_at) 
VALUES ('Test Admin', 'admin@test.com', '$2a$10$slYQmyNdGzin/eexlxAitch8.P7n.P7Q0H5WK5bAD8OqZLqPOC.g2', 'ADMIN', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Create patient profile for test patient
INSERT INTO patient_profile (user_id, age, gender, blood_group, created_at, updated_at)
VALUES (1, 30, 'Male', 'O+', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

COMMIT;

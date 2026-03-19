package com.ayubot.service;

import com.ayubot.model.User;
import com.ayubot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user with encrypted password
     */
    public User registerUser(User user) {
        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Ensure role is uppercase
        if (user.getRole() != null) {
            user.setRole(user.getRole().toUpperCase());
        }
        
        return userRepository.save(user);
    }

    /**
     * Authenticate user with email and password
     */
    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        
        // Verify password using BCrypt
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        
        return user;
    }

    public User addUser(User c) {
        Optional<User> op = userRepository.findByName(c.getName());
        if (op.isPresent()) {
            return null;
        } else {
            return userRepository.save(c);
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(User u) {
        User existing = userRepository.findById(u.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        if (u.getName() != null) existing.setName(u.getName());
        if (u.getEmail() != null) existing.setEmail(u.getEmail());
        if (u.getRole() != null) existing.setRole(u.getRole().toUpperCase());
        // if password provided, update and encrypt
        if (u.getPassword() != null && !u.getPassword().trim().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(u.getPassword()));
        }
        // preserve assigned doctor id if not provided
        if (u.getAssignedDoctorId() != null) existing.setAssignedDoctorId(u.getAssignedDoctorId());
        if (u.getAssignedAdminId() != null) existing.setAssignedAdminId(u.getAssignedAdminId());

        // doctor profile fields
        if (u.getSpecialty() != null) existing.setSpecialty(u.getSpecialty());
        if (u.getLicenseImage() != null) existing.setLicenseImage(u.getLicenseImage());
        if (u.getBio() != null) existing.setBio(u.getBio());
        if (u.getQualifications() != null) existing.setQualifications(u.getQualifications());
        if (u.getClinicAddress() != null) existing.setClinicAddress(u.getClinicAddress());
        if (u.getPhoneNumber() != null) existing.setPhoneNumber(u.getPhoneNumber());
        if (u.getVerified() != null) existing.setVerified(u.getVerified());
        return userRepository.save(existing);
    }
    
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
    
    /**
     * Get all doctors
     */
    public List<User> getAllDoctors() {
        return userRepository.findByRole("DOCTOR");
    }

    /**
     * Get doctors managed by an admin
     */
    public List<User> getDoctorsByAdmin(Long adminId) {
        return userRepository.findByAssignedAdminIdAndRole(adminId, "DOCTOR");
    }

    /**
     * Get patients managed by an admin
     */
    public List<User> getPatientsByAdmin(Long adminId) {
        return userRepository.findByAssignedAdminIdAndRole(adminId, "PATIENT");
    }

    /**
     * Create a doctor under an admin
     */
    public User createDoctorUnderAdmin(User doctor, Long adminId) {
        doctor.setRole("DOCTOR");
        doctor.setAssignedAdminId(adminId);
        // registerUser will encrypt password
        return registerUser(doctor);
    }

    /**
     * Assign an admin to a user (patient or doctor)
     */
    public User assignAdminToUser(Long userId, Long adminId) {
        User u = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        u.setAssignedAdminId(adminId);
        return userRepository.save(u);
    }
    
    /**
     * Assign a doctor to a patient
     */
    public User assignDoctor(Long patientId, Long doctorId) {
        // Verify patient exists
        User patient = userRepository.findById(patientId)
            .orElseThrow(() -> new RuntimeException("Patient not found"));
        
        // Verify patient role
        if (!"PATIENT".equals(patient.getRole())) {
            throw new RuntimeException("User is not a patient");
        }
        
        // Verify doctor exists and has DOCTOR role
        User doctor = userRepository.findById(doctorId)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        if (!"DOCTOR".equals(doctor.getRole())) {
            throw new RuntimeException("User is not a doctor");
        }
        
        // Assign doctor to patient
        patient.setAssignedDoctorId(doctorId);
        return userRepository.save(patient);
    }
    
    /**
     * Get assigned doctor for a patient
     */
    public Optional<User> getAssignedDoctor(Long patientId) {
        User patient = userRepository.findById(patientId)
            .orElseThrow(() -> new RuntimeException("Patient not found"));
        
        if (patient.getAssignedDoctorId() != null) {
            return userRepository.findById(patient.getAssignedDoctorId());
        }
        
        return Optional.empty();
    }
}

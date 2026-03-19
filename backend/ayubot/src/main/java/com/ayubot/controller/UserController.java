package com.ayubot.controller;

import com.ayubot.model.User;
import com.ayubot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.addUser(user);
        return ResponseEntity.status(201).body(createdUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/users/doctors")
    public ResponseEntity<List<User>> getAllDoctors() {
        List<User> doctors = userService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }
    
    @PostMapping("/patients/{patientId}/assign-doctor")
    public ResponseEntity<User> assignDoctor(@PathVariable Long patientId, @RequestBody AssignDoctorRequest request) {
        try {
            User updatedPatient = userService.assignDoctor(patientId, request.getDoctorId());
            return ResponseEntity.ok(updatedPatient);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/patients/{patientId}/assigned-doctor")
    public ResponseEntity<User> getAssignedDoctor(@PathVariable Long patientId) {
        try {
            Optional<User> doctor = userService.getAssignedDoctor(patientId);
            if (doctor.isPresent()) {
                return ResponseEntity.ok(doctor.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Inner class for request body
    public static class AssignDoctorRequest {
        private Long doctorId;
        
        public Long getDoctorId() {
            return doctorId;
        }
        
        public void setDoctorId(Long doctorId) {
            this.doctorId = doctorId;
        }
    }

}
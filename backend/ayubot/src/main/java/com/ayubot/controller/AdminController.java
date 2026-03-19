package com.ayubot.controller;

import com.ayubot.model.User;
import com.ayubot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    // List all users (admin view)
    @GetMapping("/users")
    public ResponseEntity<List<User>> listAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    // List doctors for an admin
    @GetMapping("/doctors")
    public ResponseEntity<List<User>> listDoctors(@RequestParam Long adminId) {
        return ResponseEntity.ok(userService.getDoctorsByAdmin(adminId));
    }

    // List patients for an admin
    @GetMapping("/patients")
    public ResponseEntity<List<User>> listPatients(@RequestParam Long adminId) {
        return ResponseEntity.ok(userService.getPatientsByAdmin(adminId));
    }

    // Create doctor under admin
    @PostMapping("/doctors")
    public ResponseEntity<User> createDoctor(@RequestParam Long adminId, @RequestBody User doctor) {
        User saved = userService.createDoctorUnderAdmin(doctor, adminId);
        return ResponseEntity.status(201).body(saved);
    }

    // Assign patient to doctor (and set admin ownership)
    @PostMapping("/assign-patient")
    public ResponseEntity<User> assignPatientToDoctor(@RequestParam Long adminId, @RequestParam Long patientId, @RequestParam Long doctorId) {
        // ensure admin ownership assigned
        userService.assignAdminToUser(patientId, adminId);
        userService.assignAdminToUser(doctorId, adminId);
        User updated = userService.assignDoctor(patientId, doctorId);
        return ResponseEntity.ok(updated);
    }

    // Change role of a user (admin)
    @PutMapping("/users/{id}/role")
    public ResponseEntity<User> changeRole(@PathVariable Long id, @RequestParam String role) {
        User u = userService.findUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        u.setRole(role.toUpperCase());
        User updated = userService.updateUser(u);
        return ResponseEntity.ok(updated);
    }

    // Delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}

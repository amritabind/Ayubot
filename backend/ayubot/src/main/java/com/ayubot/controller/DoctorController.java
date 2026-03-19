package com.ayubot.controller;

import com.ayubot.model.User;
import com.ayubot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listDoctors() {
        return ResponseEntity.ok(userService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getDoctor(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateDoctor(@PathVariable Long id, @RequestBody User doctor) {
        doctor.setId(id);
        User updated = userService.updateUser(doctor);
        return ResponseEntity.ok(updated);
    }
}

package com.ayubot.controller;

import com.ayubot.model.PatientProfile;
import com.ayubot.service.PatientProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/profiles")
public class PatientProfileController {

    @Autowired
    private PatientProfileService profileService;

    @PostMapping
    public ResponseEntity<PatientProfile> createProfile(@RequestBody PatientProfile profile) {
        try {
            PatientProfile savedProfile = profileService.saveProfile(profile);
            return ResponseEntity.status(201).body(savedProfile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PatientProfile> getProfileByUserId(@PathVariable Long userId) {
        Optional<PatientProfile> profile = profileService.getProfileByUserId(userId);
        return profile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientProfile> getProfileById(@PathVariable Long id) {
        Optional<PatientProfile> profile = profileService.getProfileById(id);
        return profile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientProfile> updateProfile(
            @PathVariable Long id,
            @RequestBody PatientProfile profile) {
        try {
            profile.setId(id);
            PatientProfile updatedProfile = profileService.updateProfile(profile);
            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        try {
            profileService.deleteProfile(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

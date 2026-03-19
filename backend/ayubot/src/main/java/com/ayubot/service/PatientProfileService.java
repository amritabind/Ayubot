package com.ayubot.service;

import com.ayubot.model.PatientProfile;
import com.ayubot.repository.PatientProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientProfileService {

    @Autowired
    private PatientProfileRepository patientProfileRepository;

    public PatientProfile saveProfile(PatientProfile profile) {
        return patientProfileRepository.save(profile);
    }

    public Optional<PatientProfile> getProfileByUserId(Long userId) {
        return patientProfileRepository.findByUserId(userId);
    }

    public Optional<PatientProfile> getProfileById(Long id) {
        return patientProfileRepository.findById(id);
    }

    public PatientProfile updateProfile(PatientProfile profile) {
        return patientProfileRepository.save(profile);
    }

    public void deleteProfile(Long id) {
        patientProfileRepository.deleteById(id);
    }
}

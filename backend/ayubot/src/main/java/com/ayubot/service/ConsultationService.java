package com.ayubot.service;

import com.ayubot.model.Consultation;
import com.ayubot.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    public Consultation saveConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    public List<Consultation> getConsultationsByPatientId(Long patientId) {
        return consultationRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }

    public List<Consultation> getConsultationsByDoctorId(Long doctorId) {
        return consultationRepository.findByDoctorIdOrderByCreatedAtDesc(doctorId);
    }

    public List<Consultation> getConsultationsByStatus(String status) {
        return consultationRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    public List<Consultation> getConsultationsByDoctorIdAndStatus(Long doctorId, String status) {
        return consultationRepository.findByDoctorIdAndStatusOrderByCreatedAtDesc(doctorId, status);
    }

    public Optional<Consultation> getConsultationById(Long id) {
        return consultationRepository.findById(id);
    }

    public void deleteConsultation(Long id) {
        consultationRepository.deleteById(id);
    }

    public Consultation updateConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }
}

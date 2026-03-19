package com.ayubot.repository;

import com.ayubot.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByPatientIdOrderByCreatedAtDesc(Long patientId);
    List<Consultation> findByDoctorIdOrderByCreatedAtDesc(Long doctorId);
    List<Consultation> findByStatusOrderByCreatedAtDesc(String status);
    List<Consultation> findByDoctorIdAndStatusOrderByCreatedAtDesc(Long doctorId, String status);
}

package com.ayubot.controller;

import com.ayubot.model.Consultation;
import com.ayubot.service.ConsultationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    private static final Logger LOG = LoggerFactory.getLogger(ConsultationController.class);

    @PostMapping
    public ResponseEntity<Consultation> createConsultation(@RequestBody Consultation consultation) {
        try {
            Consultation savedConsultation = consultationService.saveConsultation(consultation);
            return ResponseEntity.status(201).body(savedConsultation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Consultation>> getConsultationsByPatientId(@PathVariable Long patientId) {
        List<Consultation> consultations = consultationService.getConsultationsByPatientId(patientId);
        return ResponseEntity.ok(consultations);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Consultation>> getConsultationsByDoctorId(@PathVariable Long doctorId) {
        List<Consultation> consultations = consultationService.getConsultationsByDoctorId(doctorId);
        return ResponseEntity.ok(consultations);
    }

    @GetMapping("/doctor/{doctorId}/status/{status}")
    public ResponseEntity<List<Consultation>> getConsultationsByDoctorIdAndStatus(
            @PathVariable Long doctorId,
            @PathVariable String status) {
        List<Consultation> consultations = consultationService.getConsultationsByDoctorIdAndStatus(doctorId, status);
        return ResponseEntity.ok(consultations);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Consultation>> getConsultationsByStatus(@PathVariable String status) {
        List<Consultation> consultations = consultationService.getConsultationsByStatus(status);
        return ResponseEntity.ok(consultations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consultation> getConsultationById(@PathVariable Long id) {
        Optional<Consultation> consultation = consultationService.getConsultationById(id);
        return consultation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consultation> updateConsultation(
            @PathVariable Long id,
            @RequestBody Consultation consultation) {
        try {
            consultation.setId(id);
            Consultation updatedConsultation = consultationService.updateConsultation(consultation);
            return ResponseEntity.ok(updatedConsultation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateConsultationStatus(@PathVariable Long id, @RequestBody java.util.Map<String, String> body) {
        try {
            // Log incoming status update attempts for debugging
            LOG.info("updateConsultationStatus called for id={} payload={}", id, body);
            // Log Authorization header if present (useful to debug 401/403)
            // Note: obtaining headers here requires access to HttpServletRequest; we'll log if available via RequestContextHolder
            try {
                jakarta.servlet.http.HttpServletRequest req = ((org.springframework.web.context.request.ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes()).getRequest();
                String auth = req.getHeader("Authorization");
                LOG.info("Authorization header present={}", auth != null);
            } catch (Exception e) {
                LOG.debug("Could not read Authorization header: {}", e.getMessage());
            }
            String status = body.get("status");
            if (status == null) return ResponseEntity.badRequest().body("Missing status");
            java.util.Optional<Consultation> opt = consultationService.getConsultationById(id);
            if (opt.isEmpty()) return ResponseEntity.notFound().build();
            Consultation c = opt.get();
            c.setStatus(status);
            consultationService.updateConsultation(c);
            LOG.info("Consultation {} status updated to {}", id, status);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOG.error("Failed to update consultation status", e);
            return ResponseEntity.status(500).body("Failed to update status");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable Long id) {
        try {
            consultationService.deleteConsultation(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

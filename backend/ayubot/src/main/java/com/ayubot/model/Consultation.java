package com.ayubot.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "consultations")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "patient_id", nullable = false)
    private Long patientId;
    
    @Column(name = "doctor_id")
    private Long doctorId;
    
    @Column(name = "symptoms", columnDefinition = "TEXT", nullable = false)
    private String symptoms;
    
    @Column(name = "affected_area")
    private String affectedArea;
    
    @Column(name = "ai_diagnosis", columnDefinition = "TEXT")
    private String aiDiagnosis;
    
    @Column(name = "doctor_notes", columnDefinition = "TEXT")
    private String doctorNotes;
    
    @Column(name = "prescription", columnDefinition = "TEXT")
    private String prescription;
    
    @Column(name = "status")
    private String status = "PENDING";
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

package com.ayubot.service;

import com.ayubot.model.MedicalReport;
import com.ayubot.repository.MedicalReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalReportService {

    @Autowired
    private MedicalReportRepository reportRepository;

    /**
     * Save a new medical report
     */
    public MedicalReport saveReport(MedicalReport report) {
        return reportRepository.save(report);
    }

    /**
     * Get all reports for a patient
     */
    public List<MedicalReport> getReportsByPatientId(Long patientId) {
        return reportRepository.findByPatient_IdOrderByCreatedAtDesc(patientId);
    }

    /**
     * Get reports by patient ID and type
     */
    public List<MedicalReport> getReportsByPatientIdAndType(Long patientId, String reportType) {
        return reportRepository.findByPatient_IdAndReportTypeOrderByCreatedAtDesc(patientId, reportType);
    }

    /**
     * Get a specific report by ID
     */
    public Optional<MedicalReport> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    /**
     * Delete a report
     */
    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }

    /**
     * Update a report
     */
    public MedicalReport updateReport(MedicalReport report) {
        return reportRepository.save(report);
    }
}

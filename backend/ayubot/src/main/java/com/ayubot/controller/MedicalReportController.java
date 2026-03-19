package com.ayubot.controller;

import com.ayubot.model.MedicalReport;
import com.ayubot.service.MedicalReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/reports")
public class MedicalReportController {

    @Autowired
    private MedicalReportService reportService;
    
    @Autowired
    private com.ayubot.service.UserService userService;

    @Autowired
    private com.ayubot.service.ReportShareService reportShareService;

    // Return reports shared with a doctor (lightweight DTO)
    @GetMapping("/shared/doctor/{doctorId}")
    public ResponseEntity<?> getReportsSharedWithDoctor(@PathVariable Long doctorId) {
        try {
            List<com.ayubot.model.ReportShare> shares = reportShareService.getSharesByDoctorId(doctorId);
            // Build simple DTOs to avoid lazy loading issues
            List<Object> out = new java.util.ArrayList<>();
            for (com.ayubot.model.ReportShare s : shares) {
                com.ayubot.model.MedicalReport r = s.getReport();
                java.util.Map<String, Object> m = new java.util.HashMap<>();
                m.put("shareId", s.getId());
                m.put("message", s.getMessage());
                m.put("createdAt", s.getCreatedAt());
                if (r != null) {
                    java.util.Map<String, Object> rep = new java.util.HashMap<>();
                    rep.put("id", r.getId());
                    rep.put("reportName", r.getReportName());
                    rep.put("reportType", r.getReportType());
                    rep.put("createdAt", r.getCreatedAt());
                    rep.put("fileData", r.getFileData());
                    m.put("report", rep);
                }
                if (s.getSharedBy() != null) {
                    java.util.Map<String, Object> sb = new java.util.HashMap<>();
                    sb.put("id", s.getSharedBy().getId());
                    sb.put("name", s.getSharedBy().getName());
                    m.put("sharedBy", sb);
                }
                out.add(m);
            }
            return ResponseEntity.ok(out);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error fetching shared reports");
        }
    }

    @PostMapping
    public ResponseEntity<MedicalReport> createReport(@RequestBody MedicalReport report) {
        try {
            MedicalReport savedReport = reportService.saveReport(report);
            return ResponseEntity.status(201).body(savedReport);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getReportsByPatientId(@PathVariable Long patientId) {
        try {
            System.out.println("Fetching reports for patient: " + patientId);
            List<MedicalReport> reports = reportService.getReportsByPatientId(patientId);
            System.out.println("Found " + reports.size() + " reports");
            
            // Convert to DTOs to avoid lazy loading issues
            List<java.util.Map<String, Object>> dtos = new java.util.ArrayList<>();
            for (MedicalReport r : reports) {
                java.util.Map<String, Object> dto = new java.util.HashMap<>();
                dto.put("id", r.getId());
                dto.put("reportName", r.getReportName());
                dto.put("reportType", r.getReportType());
                dto.put("createdAt", r.getCreatedAt());
                dto.put("extractedText", r.getExtractedText());
                dto.put("aiAnalysis", r.getAiAnalysis());
                dto.put("fileData", r.getFileData());
                if (r.getPatient() != null) {
                    java.util.Map<String, Object> patient = new java.util.HashMap<>();
                    patient.put("id", r.getPatient().getId());
                    patient.put("name", r.getPatient().getName());
                    dto.put("patient", patient);
                }
                dtos.add(dto);
            }
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            System.err.println("Error fetching reports: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error fetching reports: " + e.getMessage());
        }
    }

    @GetMapping("/patient/{patientId}/type/{reportType}")
    public ResponseEntity<List<MedicalReport>> getReportsByPatientIdAndType(
            @PathVariable Long patientId,
            @PathVariable String reportType) {
        List<MedicalReport> reports = reportService.getReportsByPatientIdAndType(patientId, reportType);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalReport> getReportById(@PathVariable Long id) {
        Optional<MedicalReport> report = reportService.getReportById(id);
        return report.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        try {
            reportService.deleteReport(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalReport> updateReport(
            @PathVariable Long id,
            @RequestBody MedicalReport report) {
        try {
            report.setId(id);
            MedicalReport updatedReport = reportService.updateReport(report);
            return ResponseEntity.ok(updatedReport);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Share a report with a doctor
    @PostMapping("/{id}/share")
    public ResponseEntity<?> shareReport(@PathVariable Long id, @RequestBody ShareRequest req) {
        try {
            // validate report
            Optional<MedicalReport> repOpt = reportService.getReportById(id);
            if (!repOpt.isPresent()) return ResponseEntity.status(404).body("Report not found");
            MedicalReport report = repOpt.get();

            // find doctor user
            Optional<com.ayubot.model.User> docOpt = userService.findUserById(req.getDoctorId());
            if (!docOpt.isPresent()) return ResponseEntity.status(404).body("Doctor not found");

            com.ayubot.model.User doctor = docOpt.get();

            // create share record
            com.ayubot.model.ReportShare share = new com.ayubot.model.ReportShare();
            share.setReport(report);
            share.setDoctor(doctor);
            // set sharedBy as report.patient when available
            share.setSharedBy(report.getPatient());
            share.setMessage(req.getMessage());

            com.ayubot.model.ReportShare saved = reportShareService.save(share);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error sharing report");
        }
    }

    // Simple DTO for share request
    public static class ShareRequest {
        private Long doctorId;
        private String message;

        public Long getDoctorId() { return doctorId; }
        public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}

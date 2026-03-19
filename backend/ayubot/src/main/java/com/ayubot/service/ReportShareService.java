package com.ayubot.service;

import com.ayubot.model.ReportShare;
import com.ayubot.repository.ReportShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportShareService {

    @Autowired
    private ReportShareRepository reportShareRepository;

    public ReportShare save(ReportShare share){
        return reportShareRepository.save(share);
    }

    public List<ReportShare> getSharesByDoctorId(Long doctorId) {
        return reportShareRepository.findByDoctorIdOrderByCreatedAtDesc(doctorId);
    }
}

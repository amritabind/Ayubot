package com.ayubot.repository;

import com.ayubot.model.ReportShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportShareRepository extends JpaRepository<ReportShare, Long> {
	List<ReportShare> findByDoctorIdOrderByCreatedAtDesc(Long doctorId);

}

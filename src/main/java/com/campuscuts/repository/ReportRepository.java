package com.campuscuts.repository;

import com.campuscuts.entity.Report;
import com.campuscuts.entity.enums.ReportTargetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByTargetTypeAndTargetId(ReportTargetType targetType, Long targetId);
}

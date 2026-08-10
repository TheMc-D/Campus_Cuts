package com.campuscuts.repository;

import com.campuscuts.entity.CampusArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampusAreaRepository extends JpaRepository<CampusArea, Long> {

    List<CampusArea> findBySchoolId(Long schoolId);
}

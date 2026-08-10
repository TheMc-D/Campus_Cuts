package com.campuscuts.service;

import com.campuscuts.entity.School;
import com.campuscuts.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public List<School> findAll() {
        return schoolRepository.findAll();
    }

    public School getById(Long id) {
        return schoolRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("School not found: " + id));
    }
}

package com.campuscuts.service;

import com.campuscuts.dto.SignupForm;
import com.campuscuts.entity.School;
import com.campuscuts.entity.User;
import com.campuscuts.entity.enums.Role;
import com.campuscuts.repository.SchoolRepository;
import com.campuscuts.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepository, SchoolRepository schoolRepository,
                                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(SignupForm form) {
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("An account with this email already exists");
        }
        School school = schoolRepository.findById(form.getSchoolId())
                .orElseThrow(() -> new IllegalArgumentException("School not found: " + form.getSchoolId()));

        User user = new User();
        user.setFullName(form.getFullName());
        user.setEmail(form.getEmail());
        user.setPasswordHash(passwordEncoder.encode(form.getPassword()));
        user.setRole(Role.STUDENT);
        user.setSchool(school);
        return userRepository.save(user);
    }
}

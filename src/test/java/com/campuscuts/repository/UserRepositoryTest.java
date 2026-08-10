package com.campuscuts.repository;

import com.campuscuts.entity.School;
import com.campuscuts.entity.User;
import com.campuscuts.entity.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UserRepositoryTest {

    @org.springframework.beans.factory.annotation.Autowired
    private UserRepository userRepository;

    @org.springframework.beans.factory.annotation.Autowired
    private SchoolRepository schoolRepository;

    @Test
    void findByEmail_returnsSavedUser() {
        School school = schoolRepository.findAll().get(0);

        User user = new User();
        user.setFullName("Jamie Rivera");
        user.setEmail("jamie@example.edu");
        user.setPasswordHash("hashed");
        user.setRole(Role.STUDENT);
        user.setSchool(school);
        userRepository.save(user);

        assertThat(userRepository.findByEmail("jamie@example.edu")).isPresent();
        assertThat(userRepository.existsByEmail("nobody@example.edu")).isFalse();
    }
}

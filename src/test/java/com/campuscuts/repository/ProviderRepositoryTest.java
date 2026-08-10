package com.campuscuts.repository;

import com.campuscuts.entity.CampusArea;
import com.campuscuts.entity.Provider;
import com.campuscuts.entity.School;
import com.campuscuts.entity.User;
import com.campuscuts.entity.enums.Role;
import com.campuscuts.entity.enums.ServiceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ProviderRepositoryTest {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private CampusAreaRepository campusAreaRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByCampusArea_School_IdAndServiceType_filtersCorrectly() {
        School school = schoolRepository.findAll().get(0);

        CampusArea campusArea = new CampusArea();
        campusArea.setSchool(school);
        campusArea.setName("North Campus");
        campusArea = campusAreaRepository.save(campusArea);

        User user = new User();
        user.setFullName("Sam Barber");
        user.setEmail("sam@example.edu");
        user.setPasswordHash("hashed");
        user.setRole(Role.STUDENT);
        user.setSchool(school);
        user = userRepository.save(user);

        Provider provider = new Provider();
        provider.setUser(user);
        provider.setCampusArea(campusArea);
        provider.setDisplayName("Sam's Cuts");
        provider.setServiceType(ServiceType.BARBER);
        providerRepository.save(provider);

        List<Provider> barbers = providerRepository.findByCampusArea_School_IdAndServiceType(school.getId(), ServiceType.BARBER);
        List<Provider> lashTechs = providerRepository.findByCampusArea_School_IdAndServiceType(school.getId(), ServiceType.LASH_TECH);

        assertThat(barbers).extracting(Provider::getDisplayName).contains("Sam's Cuts");
        assertThat(lashTechs).isEmpty();
    }
}

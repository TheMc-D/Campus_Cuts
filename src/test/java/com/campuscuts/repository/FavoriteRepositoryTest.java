package com.campuscuts.repository;

import com.campuscuts.entity.CampusArea;
import com.campuscuts.entity.Favorite;
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

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private CampusAreaRepository campusAreaRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void existsByUserIdAndProviderId_reflectsSavedFavorite() {
        School school = schoolRepository.findAll().get(0);

        CampusArea campusArea = new CampusArea();
        campusArea.setSchool(school);
        campusArea.setName("South Campus");
        campusArea = campusAreaRepository.save(campusArea);

        User providerOwner = new User();
        providerOwner.setFullName("Robin Lash");
        providerOwner.setEmail("robin@example.edu");
        providerOwner.setPasswordHash("hashed");
        providerOwner.setRole(Role.STUDENT);
        providerOwner.setSchool(school);
        providerOwner = userRepository.save(providerOwner);

        Provider provider = new Provider();
        provider.setUser(providerOwner);
        provider.setCampusArea(campusArea);
        provider.setDisplayName("Robin's Lashes");
        provider.setServiceType(ServiceType.LASH_TECH);
        provider = providerRepository.save(provider);

        User student = new User();
        student.setFullName("Alex Student");
        student.setEmail("alex@example.edu");
        student.setPasswordHash("hashed");
        student.setRole(Role.STUDENT);
        student.setSchool(school);
        student = userRepository.save(student);

        assertThat(favoriteRepository.existsByUserIdAndProviderId(student.getId(), provider.getId())).isFalse();

        Favorite favorite = new Favorite();
        favorite.setUser(student);
        favorite.setProvider(provider);
        favoriteRepository.save(favorite);

        assertThat(favoriteRepository.existsByUserIdAndProviderId(student.getId(), provider.getId())).isTrue();
    }
}

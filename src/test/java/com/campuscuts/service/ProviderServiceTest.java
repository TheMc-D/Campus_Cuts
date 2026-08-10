package com.campuscuts.service;

import com.campuscuts.dto.ProviderApplyForm;
import com.campuscuts.entity.CampusArea;
import com.campuscuts.entity.Provider;
import com.campuscuts.entity.User;
import com.campuscuts.entity.enums.ServiceType;
import com.campuscuts.repository.AvailabilityRepository;
import com.campuscuts.repository.CampusAreaRepository;
import com.campuscuts.repository.OfferingRepository;
import com.campuscuts.repository.ProviderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProviderServiceTest {

    @Mock
    private ProviderRepository providerRepository;
    @Mock
    private CampusAreaRepository campusAreaRepository;
    @Mock
    private OfferingRepository offeringRepository;
    @Mock
    private AvailabilityRepository availabilityRepository;

    @InjectMocks
    private ProviderService providerService;

    @Test
    void applyAsProvider_rejectsWhenUserAlreadyHasProvider() {
        User user = new User();
        user.setEmail("already@example.edu");
        Provider existing = new Provider();
        // Simulate that this user already has a provider profile by wiring the inverse side.
        setProviderOnUser(user, existing);

        ProviderApplyForm form = new ProviderApplyForm();
        form.setDisplayName("Test");
        form.setServiceType(ServiceType.BARBER);
        form.setCampusAreaId(1L);

        assertThatThrownBy(() -> providerService.applyAsProvider(user, form))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void applyAsProvider_savesNewProviderWhenNoneExists() {
        User user = new User();
        user.setEmail("new@example.edu");

        CampusArea campusArea = new CampusArea();
        when(campusAreaRepository.findById(1L)).thenReturn(Optional.of(campusArea));
        when(providerRepository.save(any(Provider.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProviderApplyForm form = new ProviderApplyForm();
        form.setDisplayName("Fresh Cuts");
        form.setServiceType(ServiceType.BARBER);
        form.setCampusAreaId(1L);

        Provider saved = providerService.applyAsProvider(user, form);

        assertThat(saved.getDisplayName()).isEqualTo("Fresh Cuts");
        assertThat(saved.getServiceType()).isEqualTo(ServiceType.BARBER);
    }

    private static void setProviderOnUser(User user, Provider provider) {
        try {
            var field = User.class.getDeclaredField("provider");
            field.setAccessible(true);
            field.set(user, provider);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}

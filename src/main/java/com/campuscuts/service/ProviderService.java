package com.campuscuts.service;

import com.campuscuts.dto.AvailabilityForm;
import com.campuscuts.dto.OfferingForm;
import com.campuscuts.dto.ProviderApplyForm;
import com.campuscuts.entity.Availability;
import com.campuscuts.entity.CampusArea;
import com.campuscuts.entity.Offering;
import com.campuscuts.entity.Provider;
import com.campuscuts.entity.User;
import com.campuscuts.repository.AvailabilityRepository;
import com.campuscuts.repository.CampusAreaRepository;
import com.campuscuts.repository.OfferingRepository;
import com.campuscuts.repository.ProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final CampusAreaRepository campusAreaRepository;
    private final OfferingRepository offeringRepository;
    private final AvailabilityRepository availabilityRepository;

    public ProviderService(ProviderRepository providerRepository, CampusAreaRepository campusAreaRepository,
                            OfferingRepository offeringRepository, AvailabilityRepository availabilityRepository) {
        this.providerRepository = providerRepository;
        this.campusAreaRepository = campusAreaRepository;
        this.offeringRepository = offeringRepository;
        this.availabilityRepository = availabilityRepository;
    }

    public Provider getById(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Provider not found: " + id));
    }

    @Transactional
    public Provider applyAsProvider(User user, ProviderApplyForm form) {
        if (user.getProvider() != null) {
            throw new IllegalStateException("This account is already a provider");
        }
        CampusArea campusArea = campusAreaRepository.findById(form.getCampusAreaId())
                .orElseThrow(() -> new IllegalArgumentException("Campus area not found: " + form.getCampusAreaId()));

        Provider provider = new Provider();
        provider.setUser(user);
        provider.setCampusArea(campusArea);
        provider.setDisplayName(form.getDisplayName());
        provider.setBio(form.getBio());
        provider.setServiceType(form.getServiceType());
        provider.setLocationDetail(form.getLocationDetail());
        return providerRepository.save(provider);
    }

    @Transactional
    public Offering addOffering(Provider provider, OfferingForm form) {
        Offering offering = new Offering();
        offering.setProvider(provider);
        offering.setName(form.getName());
        offering.setPrice(form.getPrice());
        offering.setDurationMinutes(form.getDurationMinutes());
        return offeringRepository.save(offering);
    }

    @Transactional
    public void removeOffering(Provider provider, Long offeringId) {
        Offering offering = offeringRepository.findById(offeringId)
                .orElseThrow(() -> new IllegalArgumentException("Offering not found: " + offeringId));
        assertOwnedBy(offering.getProvider(), provider);
        offeringRepository.delete(offering);
    }

    @Transactional
    public Availability addAvailability(Provider provider, AvailabilityForm form) {
        Availability availability = new Availability();
        availability.setProvider(provider);
        availability.setDayOfWeek(form.getDayOfWeek());
        availability.setStartTime(form.getStartTime());
        availability.setEndTime(form.getEndTime());
        return availabilityRepository.save(availability);
    }

    @Transactional
    public void removeAvailability(Provider provider, Long availabilityId) {
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new IllegalArgumentException("Availability not found: " + availabilityId));
        assertOwnedBy(availability.getProvider(), provider);
        availabilityRepository.delete(availability);
    }

    private void assertOwnedBy(Provider actual, Provider expected) {
        if (!actual.getId().equals(expected.getId())) {
            throw new IllegalStateException("This resource does not belong to the current provider");
        }
    }
}

package com.campuscuts.service;

import com.campuscuts.dto.SearchFilterForm;
import com.campuscuts.entity.Provider;
import com.campuscuts.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final ProviderRepository providerRepository;

    public SearchService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public List<Provider> search(SearchFilterForm filter) {
        List<Provider> results;
        if (filter.getServiceType() != null) {
            results = providerRepository.findByCampusArea_School_IdAndServiceType(filter.getSchoolId(), filter.getServiceType());
        } else {
            results = providerRepository.findByCampusArea_School_Id(filter.getSchoolId());
        }

        String query = filter.getQ();
        if (query == null || query.isBlank()) {
            return results;
        }
        String needle = query.toLowerCase();
        return results.stream()
                .filter(p -> p.getDisplayName().toLowerCase().contains(needle)
                        || (p.getBio() != null && p.getBio().toLowerCase().contains(needle)))
                .toList();
    }
}

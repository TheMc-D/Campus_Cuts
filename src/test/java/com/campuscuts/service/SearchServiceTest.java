package com.campuscuts.service;

import com.campuscuts.dto.SearchFilterForm;
import com.campuscuts.entity.Provider;
import com.campuscuts.entity.enums.ServiceType;
import com.campuscuts.repository.ProviderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private SearchService searchService;

    @Test
    void search_filtersByQueryAgainstNameAndBio() {
        Provider match = provider("Jordan's Braids", "Specializing in box braids");
        Provider noMatch = provider("Taylor Cuts", "Fades and trims");

        when(providerRepository.findByCampusArea_School_Id(1L)).thenReturn(List.of(match, noMatch));

        SearchFilterForm filter = new SearchFilterForm();
        filter.setSchoolId(1L);
        filter.setQ("braid");

        List<Provider> results = searchService.search(filter);

        assertThat(results).containsExactly(match);
    }

    @Test
    void search_withServiceType_delegatesToFilteredQuery() {
        Provider barber = provider("Sam's Cuts", null);
        when(providerRepository.findByCampusArea_School_IdAndServiceType(1L, ServiceType.BARBER))
                .thenReturn(List.of(barber));

        SearchFilterForm filter = new SearchFilterForm();
        filter.setSchoolId(1L);
        filter.setServiceType(ServiceType.BARBER);

        assertThat(searchService.search(filter)).containsExactly(barber);
    }

    private static Provider provider(String name, String bio) {
        Provider provider = new Provider();
        provider.setDisplayName(name);
        provider.setBio(bio);
        return provider;
    }
}

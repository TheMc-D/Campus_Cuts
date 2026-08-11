package com.campuscuts.controller;

import com.campuscuts.config.MvcViewConfig;
import com.campuscuts.config.SecurityConfig;
import com.campuscuts.security.ProviderAccessGuard;
import com.campuscuts.service.ProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProviderDashboardController.class)
@Import({SecurityConfig.class, MvcViewConfig.class, ProviderDashboardControllerTest.TestConfig.class})
@ActiveProfiles("test")
class ProviderDashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProviderService providerService;

    @TestConfiguration
    static class TestConfig {
        // Registered explicitly by name — @Import on a bare @Component isn't reliably
        // picked up inside a @WebMvcTest slice, and @PreAuthorize resolves this bean by name.
        @Bean
        ProviderAccessGuard providerAccessGuard() {
            return new ProviderAccessGuard();
        }
    }

    @Test
    void dashboard_redirectsToLoginWhenUnauthenticated() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void dashboard_isForbiddenForNonProviderPrincipal() throws Exception {
        // A @WithMockUser principal is not an AppUserPrincipal, so ProviderAccessGuard.isProvider()
        // returns false and @PreAuthorize on the controller denies access.
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isForbidden());
    }
}

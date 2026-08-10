package com.campuscuts.controller;

import com.campuscuts.config.SecurityConfig;
import com.campuscuts.service.SchoolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(HomeController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchoolService schoolService;

    @Test
    void home_isPubliclyAccessible() throws Exception {
        when(schoolService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }
}

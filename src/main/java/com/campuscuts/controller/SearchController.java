package com.campuscuts.controller;

import com.campuscuts.dto.SearchFilterForm;
import com.campuscuts.entity.enums.ServiceType;
import com.campuscuts.security.AppUserPrincipal;
import com.campuscuts.service.SchoolService;
import com.campuscuts.service.SearchService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    private static final String SESSION_SCHOOL_ID = "browsingSchoolId";

    private final SearchService searchService;
    private final SchoolService schoolService;

    public SearchController(SearchService searchService, SchoolService schoolService) {
        this.searchService = searchService;
        this.schoolService = schoolService;
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) Long schoolId,
                          @RequestParam(required = false) ServiceType serviceType,
                          @RequestParam(required = false) String q,
                          @AuthenticationPrincipal AppUserPrincipal principal,
                          HttpSession session,
                          Model model) {
        Long effectiveSchoolId = resolveSchoolId(schoolId, principal, session);

        SearchFilterForm filter = new SearchFilterForm();
        filter.setSchoolId(effectiveSchoolId);
        filter.setServiceType(serviceType);
        filter.setQ(q);

        model.addAttribute("schools", schoolService.findAll());
        model.addAttribute("filter", filter);
        model.addAttribute("serviceTypes", ServiceType.values());
        model.addAttribute("providers", effectiveSchoolId != null ? searchService.search(filter) : java.util.List.of());
        return "search";
    }

    private Long resolveSchoolId(Long requestedSchoolId, AppUserPrincipal principal, HttpSession session) {
        if (requestedSchoolId != null) {
            session.setAttribute(SESSION_SCHOOL_ID, requestedSchoolId);
            return requestedSchoolId;
        }
        Object sessionSchoolId = session.getAttribute(SESSION_SCHOOL_ID);
        if (sessionSchoolId != null) {
            return (Long) sessionSchoolId;
        }
        if (principal != null) {
            return principal.getUser().getSchool().getId();
        }
        return null;
    }
}

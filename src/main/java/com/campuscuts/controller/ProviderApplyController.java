package com.campuscuts.controller;

import com.campuscuts.dto.ProviderApplyForm;
import com.campuscuts.repository.CampusAreaRepository;
import com.campuscuts.security.AppUserPrincipal;
import com.campuscuts.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProviderApplyController {

    private final ProviderService providerService;
    private final CampusAreaRepository campusAreaRepository;

    public ProviderApplyController(ProviderService providerService, CampusAreaRepository campusAreaRepository) {
        this.providerService = providerService;
        this.campusAreaRepository = campusAreaRepository;
    }

    @GetMapping("/provider/apply")
    public String applyForm(@AuthenticationPrincipal AppUserPrincipal principal, Model model) {
        if (principal.getUser().getProvider() != null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("applyForm", new ProviderApplyForm());
        model.addAttribute("campusAreas", campusAreaRepository.findBySchoolId(principal.getUser().getSchool().getId()));
        model.addAttribute("serviceTypes", com.campuscuts.entity.enums.ServiceType.values());
        return "provider/apply";
    }

    @PostMapping("/provider/apply")
    public String apply(@Valid @ModelAttribute("applyForm") ProviderApplyForm form, BindingResult bindingResult,
                         @AuthenticationPrincipal AppUserPrincipal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("campusAreas", campusAreaRepository.findBySchoolId(principal.getUser().getSchool().getId()));
            model.addAttribute("serviceTypes", com.campuscuts.entity.enums.ServiceType.values());
            return "provider/apply";
        }
        providerService.applyAsProvider(principal.getUser(), form);
        return "redirect:/dashboard";
    }
}

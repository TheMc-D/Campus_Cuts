package com.campuscuts.controller;

import com.campuscuts.dto.AvailabilityForm;
import com.campuscuts.dto.OfferingForm;
import com.campuscuts.entity.Provider;
import com.campuscuts.security.AppUserPrincipal;
import com.campuscuts.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard")
@PreAuthorize("@providerAccessGuard.isProvider(authentication)")
public class ProviderDashboardController {

    private final ProviderService providerService;

    public ProviderDashboardController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping
    public String dashboard(@AuthenticationPrincipal AppUserPrincipal principal, Model model) {
        Provider provider = principal.getUser().getProvider();
        model.addAttribute("provider", provider);
        model.addAttribute("offeringForm", new OfferingForm());
        model.addAttribute("availabilityForm", new AvailabilityForm());
        return "provider/dashboard";
    }

    @PostMapping("/services")
    public String addOffering(@Valid @ModelAttribute("offeringForm") OfferingForm form, BindingResult bindingResult,
                               @AuthenticationPrincipal AppUserPrincipal principal, Model model) {
        Provider provider = principal.getUser().getProvider();
        if (bindingResult.hasErrors()) {
            model.addAttribute("provider", provider);
            model.addAttribute("availabilityForm", new AvailabilityForm());
            return "provider/dashboard";
        }
        providerService.addOffering(provider, form);
        return "redirect:/dashboard";
    }

    @PostMapping("/services/{id}/delete")
    public String removeOffering(@PathVariable Long id, @AuthenticationPrincipal AppUserPrincipal principal) {
        providerService.removeOffering(principal.getUser().getProvider(), id);
        return "redirect:/dashboard";
    }

    @PostMapping("/availability")
    public String addAvailability(@Valid @ModelAttribute("availabilityForm") AvailabilityForm form, BindingResult bindingResult,
                                   @AuthenticationPrincipal AppUserPrincipal principal, Model model) {
        Provider provider = principal.getUser().getProvider();
        if (bindingResult.hasErrors()) {
            model.addAttribute("provider", provider);
            model.addAttribute("offeringForm", new OfferingForm());
            return "provider/dashboard";
        }
        providerService.addAvailability(provider, form);
        return "redirect:/dashboard";
    }

    @PostMapping("/availability/{id}/delete")
    public String removeAvailability(@PathVariable Long id, @AuthenticationPrincipal AppUserPrincipal principal) {
        providerService.removeAvailability(principal.getUser().getProvider(), id);
        return "redirect:/dashboard";
    }
}

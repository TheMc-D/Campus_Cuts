package com.campuscuts.controller;

import com.campuscuts.dto.ReviewForm;
import com.campuscuts.security.AppUserPrincipal;
import com.campuscuts.service.ProviderService;
import com.campuscuts.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final ProviderService providerService;

    public ReviewController(ReviewService reviewService, ProviderService providerService) {
        this.reviewService = reviewService;
        this.providerService = providerService;
    }

    @PostMapping("/providers/{id}/reviews")
    public String postReview(@PathVariable Long id, @Valid @ModelAttribute("reviewForm") ReviewForm form,
                              BindingResult bindingResult, @AuthenticationPrincipal AppUserPrincipal principal) {
        if (!bindingResult.hasErrors()) {
            try {
                reviewService.postReview(providerService.getById(id), principal.getUser(), form);
            } catch (IllegalStateException ignored) {
                // Already reviewed — fall through and redirect back to the profile either way.
            }
        }
        return "redirect:/providers/" + id;
    }
}

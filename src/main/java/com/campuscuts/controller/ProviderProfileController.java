package com.campuscuts.controller;

import com.campuscuts.dto.ReviewForm;
import com.campuscuts.entity.Provider;
import com.campuscuts.security.AppUserPrincipal;
import com.campuscuts.service.FavoriteService;
import com.campuscuts.service.ProviderService;
import com.campuscuts.service.ReviewService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProviderProfileController {

    private final ProviderService providerService;
    private final ReviewService reviewService;
    private final FavoriteService favoriteService;

    public ProviderProfileController(ProviderService providerService, ReviewService reviewService,
                                      FavoriteService favoriteService) {
        this.providerService = providerService;
        this.reviewService = reviewService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/providers/{id}")
    public String profile(@PathVariable Long id, @AuthenticationPrincipal AppUserPrincipal principal, Model model) {
        Provider provider = providerService.getById(id);
        model.addAttribute("provider", provider);
        model.addAttribute("reviews", reviewService.forProvider(id));
        model.addAttribute("reviewForm", new ReviewForm());

        boolean authenticated = principal != null;
        boolean isFavorited = authenticated && favoriteService.isFavorited(principal.getUser(), provider);
        boolean isOwnProfile = authenticated && principal.getUser().getProvider() != null
                && principal.getUser().getProvider().getId().equals(provider.getId());

        model.addAttribute("authenticated", authenticated);
        model.addAttribute("isFavorited", isFavorited);
        model.addAttribute("isOwnProfile", isOwnProfile);
        return "provider/profile";
    }
}

package com.campuscuts.controller;

import com.campuscuts.security.AppUserPrincipal;
import com.campuscuts.service.FavoriteService;
import com.campuscuts.service.ProviderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final ProviderService providerService;

    public FavoriteController(FavoriteService favoriteService, ProviderService providerService) {
        this.favoriteService = favoriteService;
        this.providerService = providerService;
    }

    @PostMapping("/providers/{id}/favorite")
    public String toggle(@PathVariable Long id, @AuthenticationPrincipal AppUserPrincipal principal) {
        favoriteService.toggle(principal.getUser(), providerService.getById(id));
        return "redirect:/providers/" + id;
    }

    @GetMapping("/favorites")
    public String list(@AuthenticationPrincipal AppUserPrincipal principal, Model model) {
        model.addAttribute("favorites", favoriteService.forUser(principal.getUser()));
        return "favorites/list";
    }
}

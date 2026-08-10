package com.campuscuts.controller;

import com.campuscuts.dto.MessageForm;
import com.campuscuts.entity.Conversation;
import com.campuscuts.security.AppUserPrincipal;
import com.campuscuts.service.MessagingService;
import com.campuscuts.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MessagingController {

    private final MessagingService messagingService;
    private final ProviderService providerService;

    public MessagingController(MessagingService messagingService, ProviderService providerService) {
        this.messagingService = messagingService;
        this.providerService = providerService;
    }

    @GetMapping("/messages")
    public String inbox(@AuthenticationPrincipal AppUserPrincipal principal, Model model) {
        model.addAttribute("conversations", messagingService.inboxFor(principal.getUser()));
        model.addAttribute("currentUser", principal.getUser());
        return "messaging/inbox";
    }

    @GetMapping("/messages/{conversationId}")
    public String conversation(@PathVariable Long conversationId, @AuthenticationPrincipal AppUserPrincipal principal,
                                Model model) {
        Conversation conversation = messagingService.getConversation(conversationId);
        assertParticipant(conversation, principal);
        model.addAttribute("conversation", conversation);
        model.addAttribute("messageForm", new MessageForm());
        model.addAttribute("currentUser", principal.getUser());
        return "messaging/conversation";
    }

    @PostMapping("/messages/{conversationId}")
    public String postMessage(@PathVariable Long conversationId,
                               @Valid @ModelAttribute("messageForm") MessageForm form, BindingResult bindingResult,
                               @AuthenticationPrincipal AppUserPrincipal principal, Model model) {
        Conversation conversation = messagingService.getConversation(conversationId);
        assertParticipant(conversation, principal);
        if (bindingResult.hasErrors()) {
            model.addAttribute("conversation", conversation);
            return "messaging/conversation";
        }
        messagingService.postMessage(conversation, principal.getUser(), form);
        return "redirect:/messages/" + conversationId;
    }

    @PostMapping("/providers/{id}/message")
    public String startConversation(@PathVariable Long id, @AuthenticationPrincipal AppUserPrincipal principal) {
        Conversation conversation = messagingService.startOrResume(principal.getUser(), providerService.getById(id));
        return "redirect:/messages/" + conversation.getId();
    }

    private void assertParticipant(Conversation conversation, AppUserPrincipal principal) {
        if (!messagingService.isParticipant(conversation, principal.getUser())) {
            throw new org.springframework.security.access.AccessDeniedException("Not a participant in this conversation");
        }
    }
}

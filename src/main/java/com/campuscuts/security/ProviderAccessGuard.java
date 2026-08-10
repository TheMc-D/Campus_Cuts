package com.campuscuts.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Checks provider status live from the current DB state rather than from authorities
 * captured at login time, since a student can "upgrade" to a provider mid-session.
 */
@Component
public class ProviderAccessGuard {

    public boolean isProvider(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof AppUserPrincipal principal)) {
            return false;
        }
        return principal.getUser().getProvider() != null;
    }
}

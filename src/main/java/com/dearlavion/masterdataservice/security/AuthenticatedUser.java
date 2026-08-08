package com.dearlavion.masterdataservice.security;

/** Populated onto the SecurityContext as the Authentication principal once a Bearer token verifies. */
public record AuthenticatedUser(
        String userId,
        String username,
        String email,
        /** auth-service `activeProfile` role (ADMIN | STAFF | USER). */
        String role,
        /** auth-service tenant this identity belongs to. */
        String customer
) {
}

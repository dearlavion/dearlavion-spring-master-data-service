package com.dearlavion.masterdataservice.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/** Verifies bearer tokens against auth-service-v3. No JWT library needed here: verification is
 * delegated to a remote POST /auth/verify call, not local decoding. */
@Slf4j
@Service
public class AuthClientService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${app.auth-server-url}")
    private String authServerUrl;

    /** Returns null on any failure (invalid token, auth-service unreachable) — fails closed. */
    public VerifyResponse verify(String token) {
        String url = authServerUrl + "/auth/verify";
        try {
            return restTemplate.postForObject(url, Map.of("token", token), VerifyResponse.class);
        } catch (Exception e) {
            log.error("verify failed at {}: {}", url, e.getMessage());
            return null;
        }
    }
}

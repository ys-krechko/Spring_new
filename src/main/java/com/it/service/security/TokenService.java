package com.it.service.security;

import org.springframework.security.core.Authentication;

/**
 * Interface for processing tokens
 */
public interface TokenService {

    /**
     * Generates JWT from Authentication object
     *
     * @param authentication - Authentication object created by authentication providers
     * @return - JWT
     */
    String generate(Authentication authentication);

    /**
     * Refreshes existing JWT
     *
     * @param token - JWT
     * @return - new JWT
     */
    String refresh(String token);

    /**
     * Extracts username from JWT
     *
     * @param token - JWT
     * @return - JWT subject value
     */
    String extractUsername(String token);

    /**
     * Validates incoming JWT by the special signing key used to verify any discovered JWS digital signature
     *
     * @param authToken - JWT for validation
     * @return - true, if validation was successful
     */
    boolean validate(String authToken);
}

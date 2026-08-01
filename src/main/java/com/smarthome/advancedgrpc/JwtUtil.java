/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.advancedgrpc;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import java.util.Date;

/**
 *
 * @author Adriana Dinelly - ID 25165771
 *
 * Utility class responsible for generating and validating JWT tokens.
 *
 */
public final class JwtUtil {

    // Secret key used to sign JWT tokens.
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("SmartHomeEnergySystemSecretKey2026JWTAuthentication".getBytes(StandardCharsets.UTF_8));

    // Token validity period (30 minutes).
    private static final long EXPIRATION_TIME = 30 * 60 * 1000;

    // Prevent instantiation.
    private JwtUtil() {

    }

    /**
     * Generates a JWT for the given username.
     *
     * @param username Authenticated username.
     * @return Signed JWT.
     */
    public static String generateToken(String username) {

        return Jwts.builder()
                // Store the username.
                .setSubject(username)
                // Set token creation time.
                .setIssuedAt(new Date())
                // Set expiration time.
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                // Generate the JWT.
                .compact();
    }

    /**
     * Validates a JWT.
     *
     * @param token JWT received from the client.
     * @return true if valid.
     */
    public static boolean validateToken(String token) {

        try {
            
            // Configure the secret key and validate the JWT
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);

            return true;

        } catch (Exception e) {

            return false;

        }
    }

    /**
     * Returns the username stored inside the JWT.
     *
     * @param token JWT.
     * @return Username.
     */
    public static String getUsername(String token) {

         Claims claims = Jwts.parserBuilder()
            // Configure the secret key.
            .setSigningKey(SECRET_KEY)
            // Build the parser.
            .build()
            // Parse and validate the JWT.
            .parseClaimsJws(token)
            // Retrieve the claims.
            .getBody();

        return claims.getSubject();
    }

}

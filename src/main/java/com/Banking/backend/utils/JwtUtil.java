package com.Banking.backend.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.Banking.backend.dto.response.UserResponse;
import com.Banking.backend.entity.User;
import com.Banking.backend.repository.RepositoryAccessor;


@Component
public class JwtUtil {
    private static final String SECRET_KEY =
            "dzlbyqzefsnf3run8vtyi1wx94g80fn5qol5yly2hyaf6jkqad7gqouxmvl9n6oh5ua8wf5eoy8cubzobsbxghdiclmwpx1rn0aecc2d1eom4aeahs8ijttukm7irca0";

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

public String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("roleId", user.getRole().getId());   // Optional: use role name or ID depending on your needs
    claims.put("roleName", user.getRole().getName()); // Adding both can be useful

    return generateToken(claims,user ); // Use email or user ID as the subject
}

    public boolean isTokenValid(String token, User user) {
        final String userName = extractUserName(token);
        return (userName.equals(user.getEmail())) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(
            Map<String, Object> extraClaims,User user) {
        return "Bearer "
                + Jwts.builder()
                        .setClaims(extraClaims)
                        .setSubject(user.getEmail())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .signWith(getSigningKey())
                        .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Boolean validateToken(String token) {
        final String username = extractUsername(token);
        Optional<User> user =
                RepositoryAccessor.getUserRepository().findByEmailIgnoreCase(username);
        return user.isPresent();
    }

    public UserResponse getUserAuthDetailsFromToken(String token) {
        Claims claims = extractAllClaims(token);

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(claims.getSubject()); 
        userResponse.setRoleId(claims.get("roleId", Long.class));
        userResponse.setRoleName(claims.get("roleName", String.class));
    
        return userResponse;
    }

    public static boolean isValidToken(String token) {
        try {
            // Check if authentication exists and is a JWT-based authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
            if (!(authentication instanceof JwtAuthenticationToken)) {
                System.out.println("Invalid authentication type.");
                return false;
            }
    
            // Extract token from Security Context (if needed for comparison/debugging)
            JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
            String extractedToken = jwtAuth.getToken().getTokenValue();
    
            System.out.println("Extracted Token from Context: " + extractedToken);
    
            
            return true;
    
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }
    
}

package com.Banking.backend.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Banking.backend.dto.response.UserResponse;
import com.Banking.backend.entity.Role;
import com.Banking.backend.entity.User;
import com.Banking.backend.repository.RepositoryAccessor;
import com.Banking.backend.service.UserService;
import com.Banking.backend.utils.JwtUtil;

public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired private JwtUtil jwtUtil;

    @Autowired private UserDetailsService userServices;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
    
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
    
        // 1. Validate Authorization header
        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
    
        // 2. Extract token and email (sub)
        jwt = authHeader.substring(7);
        userEmail = jwtUtil.extractUserName(jwt); // This should extract `sub`
    
        if (StringUtils.isNotEmpty(userEmail)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
    
            // 3. Load UserDetails
            UserDetails userDetails = userServices.loadUserByUsername(userEmail);
    
            // 4. Extract User data from token (including role)
            UserResponse userResponse = jwtUtil.getUserAuthDetailsFromToken(jwt);
    
            // 5. Validate token and user from DB
            Optional<User> userExists = RepositoryAccessor.getUserRepository()
                    .findByEmailIgnoreCase(userEmail);
    
            if (userExists.isPresent() && jwtUtil.validateToken(jwt)) {
                User user = userExists.get();
    
                // 6. Populate UserResponse manually
                userResponse.setId(user.getId());
                userResponse.setName(user.getName());
                userResponse.setEmail(user.getEmail());
                userResponse.setPhone(user.getPhone());
    
                // 7. Optional: You can re-check role from DB if needed
                Optional<Role> optionalUserRole =
                        RepositoryAccessor.getRoleRepository().findById(user.getRole().getId());
    
                if (optionalUserRole.isPresent()) {
                    userResponse.setRoleId(optionalUserRole.get().getId());
                }
    
                // 8. Set Authentication
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userResponse, null, userDetails.getAuthorities());
    
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
    
        // 9. Continue the filter chain
        filterChain.doFilter(request, response);
    }
    
}

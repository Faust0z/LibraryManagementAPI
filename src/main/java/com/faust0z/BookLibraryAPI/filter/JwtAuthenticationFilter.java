package com.faust0z.BookLibraryAPI.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.faust0z.BookLibraryAPI.service.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, ObjectMapper objectMapper) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            if (jwtTokenProvider.isTokenValid(jwt)) {
                final String userIdString = jwtTokenProvider.extractUserId(jwt);
                final List<String> authorityStrings = jwtTokenProvider.extractAuthorities(jwt);

                List<SimpleGrantedAuthority> authorities = (authorityStrings != null)
                        ? authorityStrings.stream().map(SimpleGrantedAuthority::new).toList()
                        : List.of();

                // Create one instead of loading it from the database for performance
                UserDetails principal = new User(userIdString, "", authorities);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        authorities
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT expired: " + e.getMessage());
        } catch (JwtException e) {
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid JWT: " + e.getMessage());
        } catch (Exception e) {
            setErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Authentication Error: " + e.getMessage());
        }
    }

    private void setErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");

        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", "N/A - Filter Level");

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
package com.example.coachbookticket.filters;

import com.example.coachbookticket.components.JwtTokenUtils;
import com.example.coachbookticket.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Bộ lọc JWT Token cho dự án CoachBookingTicket
 * - Kiểm tra token hợp lệ trong header Authorization
 * - Bỏ qua các endpoint public (login, register, xem routes, trips...)
 * - Nếu hợp lệ thì gắn user vào SecurityContext
 */
//@Profile("!dev")
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            //  Bỏ qua token cho các API public
            if (isBypassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized - Missing token");
                return;
            }

            final String token = authHeader.substring(7);
            final String phone = jwtTokenUtils.extractPhone(token);
            if (phone != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(phone);
                if(jwtTokenUtils.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized - Invalid token");
        }
    }

    /**
     * Danh sách endpoint bỏ qua kiểm tra token (public)
     */
    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of("/api/users/register", "POST"),
                Pair.of("/api/users/login", "POST"),
                Pair.of("/api/routes", "GET"),
                Pair.of("/api/routes/", "GET"),
                Pair.of("/api/trips", "GET"),
                Pair.of("/api/trips/", "GET"),
                Pair.of("/api/cars", "GET"),
                Pair.of("/api/cars/", "GET"),
                Pair.of("/api/comments", "GET"),
                Pair.of("/api/ratings", "GET")
        );

        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();

        for (Pair<String, String> bypass : bypassTokens) {
            if (requestPath.startsWith(bypass.getFirst()) &&
                    requestMethod.equalsIgnoreCase(bypass.getSecond())) {
                return true;
            }
        }

        return false;
    }
}

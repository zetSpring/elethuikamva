package elethu.ikamva.filter;

import elethu.ikamva.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        log.info("servlet path: {}", request.getServletPath());
        if (request.getServletPath().startsWith("/auth/")
                || request.getServletPath().startsWith("/h2-console")) {
            filterChain.doFilter(request, response);
            return;
        }

        var autHeader = request.getHeader(AUTHORIZATION);

        if (Objects.isNull(autHeader) || !autHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = autHeader.substring(7);
        var username = jwtService.extractUsername(jwt);

        if (Objects.nonNull(username)
                && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            boolean validDbStatus = jwtService.isValidDbStatus(jwt);
            boolean isTokenValid = jwtService.isTokenValid(jwt, userDetails);
            if (isTokenValid && validDbStatus) {
                var authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);

        //        if (Objects.nonNull(autHeader) && autHeader.startsWith("Bearer ")) {
        //            try {
        //                var token = JwtUtil.retrieveToken(autHeader);
        //                var authenticationToken = JwtUtil.validateToken(token);
        //                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //                filterChain.doFilter(request, response);
        //            } catch (Exception e) {
        //                log.info("Error logging in: {}", e.getMessage());
        //                // response.sendError(FORBIDDEN.value());
        //                JwtUtil.setAuthorizationError(response, e);
        //            }
        //        } else {
        //            filterChain.doFilter(request, response);
        //        }
    }
}

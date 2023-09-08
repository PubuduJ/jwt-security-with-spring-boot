package lk.pubudu.app.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor            // RequiredArgsConstructor will be created a constructor with any final fields that declared inside the class.
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // This is authentication filter class.
    // Every time gets a request to the backend, Jwt authentication filter will be executed.

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Get incoming request Authorization header.
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // If token is not there or invalid format, go to the next filter in the filter chain.
            filterChain.doFilter(request, response);
            return;
        }

        // Extract token from authorization header.
        // 'Bearer ' takes 7 digits, Hence rest is the token.
        final String jwt = authHeader.substring(7);
        // Extract userEmail from the JWT token.
        final String userEmail = jwtService.extractUsername(jwt);
    }

}

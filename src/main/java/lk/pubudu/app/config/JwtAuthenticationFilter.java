package lk.pubudu.app.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor            // RequiredArgsConstructor will be created a constructor with any final fields that declared inside the class.
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // This is authentication filter class.
    // Every time gets a request to the backend, Jwt authentication filter will be executed.

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

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
        final String jwtToken = authHeader.substring(7);
        // Extract userEmail from the JWT token.
        final String userEmail = jwtService.extractUsername(jwtToken);

        // Check whether userEmail is present and user is not authenticated yet by using SecurityContextHolder.getContext().getAuthentication() == null.
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Get user from the database as user details.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // If token is valid we need to update the security context holder and send the request to dispatcher servlet.
            if (jwtService.isTokenValid(jwtToken, userDetails)) {

                // Create object of type UsernamePasswordAuthenticationToken as userDetails, credentials and authorities as parameters.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // set details to authToken from our request.
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Update the security context holder.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}

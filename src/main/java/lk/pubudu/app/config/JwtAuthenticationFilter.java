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

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

    }

}

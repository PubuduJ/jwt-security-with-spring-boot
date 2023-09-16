package lk.pubudu.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    // This class is used to bind the JwtAuthenticationFilter with the Spring Boot Backend.
    // At the application startup, Spring will try to look up a bean of SecurityFilterChain.
    // SecurityFilterChain bean is responsible for configuring all the http security of the application.

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**")                   // All the requests which comes to these end points will not be authenticated. (No need of token)
                .permitAll()
                .anyRequest()                                               // All the other requests will be authenticated.
                .authenticated()
                .and()                                                      // and will add new configuration.
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)     // session will be stateless.
                .and()
                .authenticationProvider(authenticationProvider)             //
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // add created JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter.
                // Because after updating the security context holder at the end of the JwtAuthenticationFilter, it will call the UsernamePasswordAuthenticationFilter next.
        return httpSecurity.build();
    }
}

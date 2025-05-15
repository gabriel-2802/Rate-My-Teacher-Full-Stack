package app.demo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailService userDetailsService;
    private final JWTAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC (no auth)
                        .requestMatchers(HttpMethod.GET,
                                "/api/teachers", "/api/teachers/*",
                                "/api/courses",  "/api/courses/*",
                                "/api/universities", "/api/universities/*").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/auth/login", "/api/auth/register").permitAll()
                        .requestMatchers("/").permitAll()

                        // AUTHENTICATED NORMAL USERS
                        // post reviews + view reviews + manage their profile + delete own reviews
                        .requestMatchers(HttpMethod.POST,
                                "/api/teachers/*/reviews",
                                "/api/courses/*/reviews",
                                "/api/universities/*/reviews").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET,
                                "/api/reviews/user/*",
                                "/api/reviews/teacher/*",
                                "/api/reviews/university/*").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT,  "/api/profile").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET,  "/api/profile").hasAnyRole("USER", "ADMIN")
                        // Allow users to delete their own reviews (validation happens in service layer)
                        .requestMatchers(HttpMethod.DELETE, "/api/reviews/*").hasAnyRole("USER", "ADMIN")

                        // ADMIN ONLY
                        // create/update/delete teachers/courses/universities + view stats
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,
                                "/api/teachers",
                                "/api/courses",
                                "/api/universities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/teachers/*",
                                "/api/courses/*",
                                "/api/universities/*",
                                "/api/reviews/*/approve").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/teachers/*",
                                "/api/courses/*",
                                "/api/reviews/*/refuse",
                                "/api/universities/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,
                                "/api/reviews",
                                "/api/reviews/pending",
                                "/api/admin/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/admin/statistics").hasRole("ADMIN")

                        // ANYTHING ELSE DENY
                        .anyRequest().denyAll()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint))
                .httpBasic(withDefaults());

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JWTAuthFilter jwtAuthenticationFilter() {
        return new JWTAuthFilter();
    }
}
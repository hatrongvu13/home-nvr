package com.xxx.config;

import com.xxx.utils.security.jwt.JwtAuthentication;
import com.xxx.utils.security.jwt.JwtFilter;
import com.xxx.utils.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthentication authentication;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtFilter customFilter = new JwtFilter(tokenProvider);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> corsConfigurationSource())
//                .cors(Customizer.withDefaults())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(authentication))
                .authorizeHttpRequests(authentication ->
                        authentication
                                .requestMatchers("/api/v1/user/register").permitAll()
                                .requestMatchers("/api/v1/user/login").permitAll()
                                .requestMatchers("/actuator").permitAll()
                                .requestMatchers("/graphiql").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withUsername("demo").password("demo").roles("USER").build();
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

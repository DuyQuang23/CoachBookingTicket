package com.example.coachbookticket.config;

import com.example.coachbookticket.components.JwtTokenUtils;
import com.example.coachbookticket.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;
//@Profile("!dev")
@Configuration
@EnableWebSecurity(debug = true)
//@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Thêm bộ lọc JWT vào trước UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> requests
                        //  Các API public
                        .requestMatchers("/api/users/register", "api/users/login").permitAll()
                        .requestMatchers("/error").permitAll()

                        .requestMatchers(PUT,"/api/users/**").hasAnyRole("ADMIN","CUSTOMER")

                        .requestMatchers(GET, "/api/routes/**").permitAll()
                        .requestMatchers(GET, "/api/trips/**").permitAll()
                        .requestMatchers(GET, "/api/cars/**").permitAll()
                        .requestMatchers(GET, "/api/ratings/**").permitAll()
                        .requestMatchers(GET, "/api/comments/**").permitAll()

                        //  routes
                        .requestMatchers(POST, "/api/routes/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers(PUT, "/api/routes/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers(DELETE, "/api/routes/**").hasRole("ADMIN")

                        //  trips
                        .requestMatchers(POST, "/api/trips/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers(PUT, "/api/trips/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers(DELETE, "/api/trips/**").hasRole("ADMIN")

                        //  Vé
                        .requestMatchers(GET, "/api/tickets/**").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                        .requestMatchers(POST, "/api/tickets/**").hasRole("CUSTOMER")
                        .requestMatchers(PUT, "/api/tickets/**").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(DELETE, "/api/tickets/**").hasRole("ADMIN")

                        //  Xe
                        .requestMatchers(POST, "/api/cars/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers(PUT, "/api/cars/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers(DELETE, "/api/cars/**").hasRole("ADMIN")

                        // Tài xế
                        .requestMatchers(POST, "/api/drivers/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers(PUT, "/api/drivers/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers(DELETE, "/api/drivers/**").hasRole("ADMIN")

                        // Bình luận và đánh giá
                        .requestMatchers(POST, "/api/comments/**").hasRole("CUSTOMER")
                        .requestMatchers(POST, "/api/ratings/**").hasRole("CUSTOMER")

                        // Payment
                        .requestMatchers("/api/payment/**").hasRole("CUSTOMER")

                        // Mọi request khác yêu cầu xác thực
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        //  Cấu hình CORS cho frontend
        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> cors) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:3000"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                cors.configurationSource(source);
            }
        });

        return http.build();
    }
}

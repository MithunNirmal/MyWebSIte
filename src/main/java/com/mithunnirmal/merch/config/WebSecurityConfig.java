package com.mithunnirmal.merch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.mithunnirmal.merch.enums.Permission.*;
import static com.mithunnirmal.merch.enums.UserRole.ADMIN;
import static com.mithunnirmal.merch.enums.UserRole.USER;
import static org.springframework.http.HttpMethod.*;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig{

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    @Autowired
    private LogoutHandler logoutHandler;

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/public/**",

            "/api/v1/auth/**",
            "/api/v1/verify/**",

            "/api/v1/album/public/**",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.info("adding filter chain " + WHITE_LIST_URL[0]);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                    .requestMatchers( WHITE_LIST_URL).permitAll()
                                .requestMatchers(DELETE,"/api/v1/user/delete/**").hasAnyRole(USER.name())
//                        .requestMatchers("/api/v1/album/admin/**").hasAnyRole(ADMIN.name(), MANAGER_READ.name())
//                        .requestMatchers(GET, "/api/v1/album/admin/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
//                        .requestMatchers(POST, "/api/v1/album/admin/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
//                        .requestMatchers(PUT, "/api/v1/album/admin/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
//                        .requestMatchers(DELETE, "/api/v1/album/admin/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

                    .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

//                .formLogin(form -> form
//                        .loginPage("/login")//You can change the login page url here if you need!
//                        .permitAll()
//                )
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
//                        .allowedHeaders("*")//"Authorization", "Content-Type") // Specify the allowed request headers
                       // .allowCredentials(true); // Allow credentials to be sent with the request
                      //  .allowedOrigins("http://localhost:3000/")
                ;
            }
        };
    }
}
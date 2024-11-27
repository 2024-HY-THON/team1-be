package com.example.hyppp.SpringSecurity;

import com.example.hyppp.SpringSecurity.jwt.JWTFilter;
import com.example.hyppp.SpringSecurity.jwt.JWTUtil;
import com.example.hyppp.SpringSecurity.jwt.LoginFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig { //로그인시 폼데이터형식으로. 나머지는 json도 가능

    //authenticationManager파라미터로 필요
    private final AuthenticationConfiguration authenticationConfiguration;

    //LoginFilter/JWTFilter에 필요
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .csrf(csrf->csrf.disable());

        http
                .formLogin((formLogin)->formLogin.disable());

        http
                .httpBasic((httpBasic)->httpBasic.disable());

        http
                .authorizeHttpRequests((authorizeRequests)-> authorizeRequests
                        .requestMatchers("/login", "/", "/register").permitAll()
                        .anyRequest().authenticated());

        http
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .addFilterAfter(new JWTFilter(jwtUtil), LoginFilter.class); //LoginFilter앞에 등록

        http.
                addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, objectMapper), UsernamePasswordAuthenticationFilter.class);


        http
                .cors((cors)->cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request)
                    {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        config.setExposedHeaders(Collections.singletonList("Authorization"));

                        return config;
                    }
                }));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

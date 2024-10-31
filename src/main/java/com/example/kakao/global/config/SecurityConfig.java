package com.example.kakao.global.config;

import java.util.Arrays;
import javax.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final String[] whiteList = {
        "/api/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .formLogin(AbstractHttpConfigurer::disable);       // (1)
        http
            .httpBasic(AbstractHttpConfigurer::disable);       // (2)
        http
            .csrf(AbstractHttpConfigurer::disable);            // (3)

        http
            .cors(
                (corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource())));

        http
            .headers(headers -> headers.frameOptions(
                HeadersConfigurer.FrameOptionsConfig::sameOrigin
            ));                                                 // (5)


        http
            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );                                                  // (7)

        http
            .authorizeHttpRequests(
                auth ->
                    auth                                        // (8)
                        .dispatcherTypeMatchers(DispatcherType.ASYNC).permitAll()
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .anyRequest().permitAll()
            );                                                  // (9)


        return http.build();
    }

    @Bean
// CORS 설정
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.addAllowedMethod(CorsConfiguration.ALL); // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader(CorsConfiguration.ALL); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 자격 증명 허용 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 구성 적용
        return source;
    }

}

package com.c9Pay.userservice.security;

import com.c9Pay.userservice.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


/**
 * Spring Security 설정을 구성하는 클래스
 */
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter authenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    /**
     * h2-console에 대한 접근을 허용한다.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) ->
                web.ignoring().requestMatchers(
                        new AntPathRequestMatcher("/h2-console/**"),
                        new AntPathRequestMatcher("/favicon.ico"),
                        new AntPathRequestMatcher( "/swagger-ui/**"),
                        new AntPathRequestMatcher("/swagger-resources/**"),
                        new AntPathRequestMatcher("/v3/api-docs/**"));
    }

    /**
     * Security Filter Chain을 설정한다.
     *
     * @param http HttpSecurity 객체
     * @return Security Filter Chain 객체
     * @throws Exception 예외
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(auth ->  auth
                        .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/user-service/api/user/signup")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/user-service/api/login")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/user-service/api/user/check-duplicate/*")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/user-service/api/docs")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/user-service/api/swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/user-service/v3/api-docs/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/webjars/swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/**")).hasAuthority("USER")
                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider)
                .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint))
                .csrf(AbstractHttpConfigurer::disable)
                .headers((header) -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(s -> s.sessionCreationPolicy(STATELESS))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(withDefaults())
                .build();
    }


    /**
     * AuthenticationManager를 빈으로 등록한다.
     *
     * @param authenticationConfiguration AuthenticationConfiguration 객체
     * @return AuthenticationManager 객체
     * @throws Exception 예외
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://web.teamcloudnine.link");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

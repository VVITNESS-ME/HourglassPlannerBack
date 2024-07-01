package com.myweapon.hourglass.config;

import com.myweapon.hourglass.security.jwt.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;
    private final CorsConfigurationSource corsConfigurationSource;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //UsernamePasswordAuthenticationFilter는 http요청으로부터 이름과 비밀번호를 추출하고 Authentication 객체를 준비
        //이것은 db에서 실제로 가져와서 비교까지 하는건지 아니면, 요청으로부터 온 데이터만으로 Authentication을 만드는지는 모르겠음.
        AuthenticationEntryPoint authenticationEntryPoint = ((request, response, authException)
                -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"));
        AccessDeniedHandler accessDeniedHandler = ((request, response, accessDeniedException)
                -> response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"));

        http.csrf(AbstractHttpConfigurer::disable).cors(corsConfigurer->corsConfigurer.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(request -> request.requestMatchers("/user/**")
                        .permitAll().anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandlingConfigurer->exceptionHandlingConfigurer
                        .authenticationEntryPoint(authenticationEntryPoint) //인증 실패시 에러 처리
                        .accessDeniedHandler(accessDeniedHandler) //인가 실패시 에러 처리
                );

        return http.build();
    }

    //인증 성공, 실패를 선택한다.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}

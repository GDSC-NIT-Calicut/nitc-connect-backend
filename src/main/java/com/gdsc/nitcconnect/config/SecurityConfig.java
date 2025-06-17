package com.gdsc.nitcconnect.config;

import com.gdsc.nitcconnect.repository.UserRepository;
import com.gdsc.nitcconnect.service.CustomOAuth2UserService;
import com.gdsc.nitcconnect.service.JwtService;
import com.gdsc.nitcconnect.service.OAuth2LoginSuccessHandler;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;

    // Inject the active profile (optional but useful for more complex logic)
    @Value("${server.ssl.enabled:false}")
    private boolean isSslEnabled;

    public SecurityConfig(CustomOAuth2UserService oAuth2UserService) {
        this.oAuth2UserService = oAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler,
                                                   JwtService jwtService,
                                                   UserRepository userRepository) throws Exception {

        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**") // Ignore CSRF for API if needed
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/error", "/login**", "/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)
                        )
                        .successHandler(oAuth2LoginSuccessHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(this::handleLogout)
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtService, userRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ Custom logout handler to clear token and redirect
    private void handleLogout(HttpServletRequest request, HttpServletResponse response,
                              org.springframework.security.core.Authentication authentication) throws IOException {
        // Invalidate session
        request.getSession().invalidate();

        // Check if we're on HTTPS (in production)
        boolean isSecure = isSslEnabled || !"localhost".equals(request.getServerName());

        // Clear token cookie
        Cookie tokenCookie = new Cookie("token", null);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(isSecure);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(0);
        response.addCookie(tokenCookie);

        // Clear session cookie
        Cookie jsessionCookie = new Cookie("JSESSIONID", null);
        jsessionCookie.setPath("/");
        jsessionCookie.setSecure(isSecure);
        jsessionCookie.setMaxAge(0);
        response.addCookie(jsessionCookie);

        // Redirect to home
        response.sendRedirect("/");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

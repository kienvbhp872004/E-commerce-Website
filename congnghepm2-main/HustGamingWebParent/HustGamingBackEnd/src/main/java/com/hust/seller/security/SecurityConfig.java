package com.hust.seller.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler successHandler;


    public SecurityConfig(UserDetailsService userDetailsService, CustomUserDetailsService customUserDetailsService, CustomAuthenticationSuccessHandler successHandler) {
        this.userDetailsService = userDetailsService;
        this.customUserDetailsService = customUserDetailsService;
        this.successHandler = successHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Mã hóa mật khẩu bằng BCrypt
    }
    // Cấu hình AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session
                        .sessionFixation().newSession()  // Tạo session mới khi đăng nhập
                        .maximumSessions(1)  // Giới hạn mỗi người dùng chỉ có 1 session hoạt động
                        .expiredUrl("/login?expired")  // Chuyển hướng sau khi session hết hạn
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .requestMatchers("/seller/**").hasAnyRole("SELLER")
                        .requestMatchers("/customer/**").hasAnyRole("CUSTOMER")
                        .requestMatchers("/login", "/register", "/forgot-password","/","/error/**","reset-password","/check-login-status","/products/**","/categories/**","/shop/**","/search/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(successHandler) // Sử dụng Custom Authentication Success Handler
                        .failureHandler(new CustomAuthenticationFailureHandler())
                        .permitAll()  // Cho phép tất cả mọi người truy cập trang login
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // URL để đăng xuất
                        .logoutSuccessUrl("/login?logout")  // Chuyển hướng sau khi đăng xuất thành công
                        .invalidateHttpSession(true)  // Hủy session khi đăng xuất
                        .deleteCookies("JSESSIONID","remember-me")  // Xóa cookie
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(5*60)  // Remember me token 1 ngày
                );

        return http.build();
    }

}

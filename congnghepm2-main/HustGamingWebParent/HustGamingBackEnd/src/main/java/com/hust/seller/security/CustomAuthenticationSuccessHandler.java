package com.hust.seller.security;

import com.hust.seller.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.io.IOException;
@Component

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private  CustomUserDetailsService customUserDetailsService;

    public CustomAuthenticationSuccessHandler(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

   @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
       SecurityContextHolder.clearContext();
       // Lấy tên người dùng từ authentication
        String role = authentication.getAuthorities().stream()
                .findFirst() // Giả định rằng người dùng có một vai trò
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("");

        // Điều hướng theo vai trò
        if (role.equals("ROLE_ADMIN")) {
            response.sendRedirect("/admin/"); // Đường dẫn cho admin
        } else if (role.equals("ROLE_SELLER")) {
            response.sendRedirect("/seller/"); // Đường dẫn cho seller
        } else if (role.equals("ROLE_CUSTOMER")) {
            response.sendRedirect(""); // Đường dẫn cho customer
        } else {
            response.sendRedirect("/login?error=true"); // Đường dẫn mặc định nếu không xác định được vai trò
        }
    }

}

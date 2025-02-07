package com.hust.seller.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "Đăng nhập thất bại. Vui lòng thử lại.";
        if (exception instanceof DisabledException) {
            errorMessage = "Tài khoản đã bị khoá.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "Tên đăng nhập không hợp lệ.";
        }
        // Lưu thông báo lỗi vào session để hiển thị trên trang đăng nhập
        request.getSession().setAttribute("error", errorMessage);
        response.sendRedirect("/login?error=true"); // Điều hướng người dùng đến trang đăng nhập với thông báo lỗi
    }
}
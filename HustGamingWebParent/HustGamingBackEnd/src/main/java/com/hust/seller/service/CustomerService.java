package com.hust.seller.service;

import com.hust.seller.entity.User;
import com.hust.seller.security.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private UserRepository userRepository;

    public Integer getCurrentCustomerID() {
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUserID(); // Trả về ID của người dùng hiện tại
        }
        return null; // Trả về null nếu không có người dùng nào đang đăng nhập
    }

    public User getCurrentUser() {
        String username = getCurrentUsername();
        if (username != null) {
            return userRepository.findByUsername(username).orElse(null);
        }
        return null;
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            return authentication.getName(); // Lấy tên đăng nhập của người dùng
        }
        return null;
    }
}

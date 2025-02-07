package com.hust.seller.admin;

import com.hust.seller.entity.User;
import com.hust.seller.security.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public List<User> findUserByRole(String role){
        return userRepository.findByRoles_RoleName(role);
    }

}

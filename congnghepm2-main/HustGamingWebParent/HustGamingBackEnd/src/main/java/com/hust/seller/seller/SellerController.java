package com.hust.seller.seller;

import com.hust.seller.entity.User;
import com.hust.seller.security.CustomUserDetailsService;
import com.hust.seller.repository.UserRepository;
import com.hust.seller.user.UserDTO;
import com.hust.seller.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/seller")
public class SellerController {
    private UserService userService;
    private CustomUserDetailsService customUserDetailsService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public SellerController(UserService userService, CustomUserDetailsService customUserDetailsService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String sellerHome(Model model) {
        SellerController sellerController = new SellerController(userService,customUserDetailsService,userRepository,passwordEncoder);
        User currentUser = customUserDetailsService.getCurrentUser();
        String fullname=currentUser.getFullName();
        model.addAttribute("nameuser", fullname);
        return "seller/home";
    }
    @GetMapping("/profile")
    public String showProfile(Model model) {
        User user = customUserDetailsService.getCurrentUser();
        model.addAttribute("user", user);
        return "seller/profile";
    }
    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") int userId, Model model) {
        Optional<User> userOptional = userRepository.findByUserID(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO();
            // Sao chép dữ liệu từ `User` sang `UserDTO`
            userDTO.setUsername(user.getUsername());
            //khong xu ly set mat khau vi bao mat thong tin
            userDTO.setEmail(user.getEmail());
            userDTO.setFullName(user.getFullName());
            userDTO.setAddress(user.getAddress());
            userDTO.setPhoneNumber(user.getPhoneNumber());
            model.addAttribute("userDTO", userDTO);
            model.addAttribute("user", user); // Có thể dùng trong form nếu cần
            return "seller/edit"; // Tên file HTML chứa form chỉnh sửa
        } else {
            return "redirect:/seller?error=UserNotFound";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") int userId, @ModelAttribute("userDTO") UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByUserID(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Kiểm tra trùng email hoặc username
            if (!user.getEmail().equals(userDTO.getEmail()) && userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
                return "error/emailexsist";
            }
            //khong can thiet vi username disabled
//            if (!user.getUsername().equals(userDTO.getUsername()) && userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
//                return "error/emailexsist";
//           }
            // Cập nhật các trường từ `UserDTO` sang `User`
            user.setFullName(userDTO.getFullName());
            user.setAddress(userDTO.getAddress());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setEmail(userDTO.getEmail());
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }
            userRepository.save(user);
            return "seller/success";
        } else {
            return "seller/fairlure";
        }
    }
}









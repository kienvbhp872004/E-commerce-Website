package com.hust.seller.admin;

import com.hust.seller.entity.Role;
import com.hust.seller.entity.User;
import com.hust.seller.security.CustomUserDetailsService;
import com.hust.seller.security.UserRepository;
import com.hust.seller.user.UserDTO;
import com.hust.seller.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;
    private UserService userService;
    private CustomUserDetailsService customUserDetailsService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AdminController(AdminService adminService, CustomUserDetailsService customUserDetailsService, UserService userService,UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.adminService = adminService;
        this.customUserDetailsService = customUserDetailsService;
        this.userService = userService;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public String getfullname(HttpServletRequest httpServletRequest) {
        String username = customUserDetailsService.getCurrentUser(httpServletRequest);
        Optional<User> user = userService.findByUsername(username);
        User user1 = user.get();
        String name = user1.getFullName();
        return name;
    }

    @GetMapping("/")
    public String adminHome(Model model, HttpServletRequest httpServletRequest) {
        AdminController adminController = new AdminController(adminService, customUserDetailsService, userService,userRepository,passwordEncoder);
        String fullname = adminController.getfullname(httpServletRequest);
        model.addAttribute("nameuser", fullname);
        return "admin/home";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpServletRequest httpServletRequest) {
        List<User> admins = adminService.findUserByRole("ROLE_ADMIN");
        AdminController adminController = new AdminController(adminService, customUserDetailsService, userService,userRepository,passwordEncoder);
        String fullname = adminController.getfullname(httpServletRequest);
        model.addAttribute("admin", admins);
        model.addAttribute("nameuser", fullname);
        return "admin/profile";
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
            return "admin/edit"; // Tên file HTML chứa form chỉnh sửa
        } else {
            return "redirect:/admin?error=UserNotFound";
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
            return "redirect:/admin/";
        } else {
            return "error/emailexsist";
        }
    }
}


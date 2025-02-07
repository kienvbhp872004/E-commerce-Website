package com.hust.seller.customer;


import com.hust.seller.entity.User;

import com.hust.seller.repository.UserRepository;
import com.hust.seller.security.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import java.util.UUID;

@Controller
@RequestMapping("/customer")
public class CustomerProfileController {
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomerService customerService;

    public CustomerProfileController(UserRepository userRepository, CustomerService customerService) {
        this.userRepository = userRepository;
        this.customUserDetailsService = new CustomUserDetailsService(userRepository);
        this.customerService = customerService; // Tiêm phụ thuộc CustomerService
    }

    @GetMapping("/profile")
    public String getProfileCustomer(Model model) {
        User user = customUserDetailsService.getCurrentUser();
        List<String> addressDetail = user.getAddressDetail();
        model.addAttribute("address", addressDetail);
        model.addAttribute("user", user);  // Add user to the model
        return "customer/profile";  // Return the view name for the profile page
    }
    @PostMapping("/profile/save-information")
    public String saveInformationCustomer( @RequestParam("full_name") String fullName,
                                           @RequestParam("phone_number") String phoneNumber,
                                           @RequestParam("tinh")String tinh,
                                           @RequestParam("quan")String huyen,
                                           @RequestParam("phuong")String phuong,
                                           @RequestParam("detailed_address")String address,
                                           @ModelAttribute("user") User user) {
        User user1 = customUserDetailsService.getCurrentUser() ;
        user1.setAddress(tinh +"-"+huyen+"-"+ phuong+"-"+address);
        user1.setFullName(fullName);
        user1.setPhoneNumber(phoneNumber);
        userRepository.save(user1) ;
        return "redirect:/customer/profile" ;
    }
    @PostMapping("/profile/save-image")
    public String saveImageCustomer(@RequestParam("avatar") MultipartFile file) {
        if (file.isEmpty()) {
            return "error/file-empty";
        }

        try {
            // Validate file is an image
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return "error/invalid-file-type";
            }

            // Sanitize filename
            String originalFilename = file.getOriginalFilename();
            String safeFileName = UUID.randomUUID().toString() + "_" +
                    originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");

            // Secure directory path
            User currentUser = customUserDetailsService.getCurrentUser();
            String uploadDir = "static/images/users/" + currentUser.getUserID();

            // Create directory safely
            Path directory = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(directory);

            // Save file
            Path targetLocation = directory.resolve(safeFileName);
            file.transferTo(targetLocation.toFile());

            // Save image URL to user
            String imageUrl = "/images/users/" + currentUser.getUserID() + "/" + safeFileName;

            currentUser.setImage(imageUrl);
            userRepository.save(currentUser);

            return "redirect:/customer/profile";

        } catch (IOException e) {
            // Use proper logging
            return "error/upload-failed";
        }
    }
}
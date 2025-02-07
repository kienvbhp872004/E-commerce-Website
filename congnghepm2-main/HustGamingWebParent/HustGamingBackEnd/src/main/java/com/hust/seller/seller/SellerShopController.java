package com.hust.seller.seller;

import com.hust.seller.entity.Shop;
import com.hust.seller.entity.User;
import com.hust.seller.security.CustomUserDetailsService;
import com.hust.seller.repository.ShopRepository;
import com.hust.seller.repository.UserRepository;
import com.hust.seller.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/seller")
public class SellerShopController {
    private UserService userService;
    private CustomUserDetailsService customUserDetailsService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ShopRepository shopRepository;

    public SellerShopController(UserService userService, CustomUserDetailsService customUserDetailsService, UserRepository userRepository, PasswordEncoder passwordEncoder, ShopRepository shopRepository) {
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.shopRepository = shopRepository;
    }


    @GetMapping("/shop")
    public String viewOrCreateShop(Model model) {
        User currentUser = customUserDetailsService.getCurrentUser();
        model.addAttribute("user",currentUser);
        // Kiểm tra xem shop đã tồn tại chưa bằng cách tìm theo sellerId
        Optional<Shop> optionalShop = shopRepository.findBySellerID(currentUser.getUserID());
        if (optionalShop.isPresent()) {
            // Nếu shop đã tồn tại, hiển thị thông tin shop và nút chỉnh sửa
            model.addAttribute("shop", optionalShop.get());
            return "seller/shop"; // Giao diện hiển thị thông tin shop
        } else {
            // Nếu chưa có shop, chuyển hướng đến trang tạo shop
            model.addAttribute("shop", new Shop());
            return "seller/createshop";
        }
    }

    @PostMapping("/shop/create")
    public String createShop(@ModelAttribute("shop") Shop shop,Model model) {
        User currentUser = customUserDetailsService.getCurrentUser();
        model.addAttribute("user",currentUser);
        shop.setSellerID(currentUser.getUserID());
        shopRepository.save(shop);
        return "seller/success";
    }

    @GetMapping("/shop/edit/{id}")
    public String showeditShop(@PathVariable("id") int id, Model model) {
        User currentUser = customUserDetailsService.getCurrentUser();
        model.addAttribute("user",currentUser);
        Optional<Shop> shop1 = shopRepository.findByShopID(id);
        Shop shop = shop1.get();
        // Kiểm tra xem người dùng hiện tại có phải chủ sở hữu shop không,tranh truong hop nguoi khac sua api de truy cap trai phep
        if (shop.getSellerID()!= currentUser.getUserID()) {
            return "seller/fairlure";
        }
        model.addAttribute("shop", shop);
        return "seller/editshop";
    }
    @PostMapping("/shop/edit/{id}")
    public String editShop(@PathVariable("id") int id, @ModelAttribute("shop") Shop shop,Model model) {
        shopRepository.save(shop);
        User currentUser = customUserDetailsService.getCurrentUser();
        model.addAttribute("user",currentUser);
        return "seller/success";
    }

    @PostMapping("/shop/save-image")
    public String saveImageCustomer(@RequestParam("avatar") MultipartFile file) {
        if (file.isEmpty()) {
            return "error/file-empty";
        }
        User currentUser = customUserDetailsService.getCurrentUser();
       Optional<Shop> shop1=shopRepository.findBySellerID(currentUser.getUserID());
      Shop shop=shop1.get();

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
            String uploadDir = "static/images/shops/" + shop.getShopID();
            // Create directory safely
            Path directory = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(directory);
            // Save file
            Path targetLocation = directory.resolve(safeFileName);
            file.transferTo(targetLocation.toFile());
            // Save image URL to user
            String imageUrl = "/images/shops/" + shop.getShopID() + "/" + safeFileName;
            shop.setImage(imageUrl);
            shopRepository.save(shop);
            return "redirect:/seller/shop";
        } catch (IOException e) {
            // Use proper logging
            return "error/upload-failed";
        }
    }

}


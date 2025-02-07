package com.hust.seller.admin;

import com.hust.seller.entity.Category;
import com.hust.seller.entity.Shop;
import com.hust.seller.entity.User;
import com.hust.seller.repository.CategoryRepository;
import com.hust.seller.security.CustomUserDetailsService;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private AdminService adminService;
    private UserService userService;
    private CustomUserDetailsService customUserDetailsService;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private PasswordEncoder passwordEncoder;

    public AdminCategoryController(AdminService adminService, UserService userService, CustomUserDetailsService customUserDetailsService, UserRepository userRepository, PasswordEncoder passwordEncoder,CategoryRepository categoryRepository) {
        this.adminService = adminService;
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository=categoryRepository;
    }
    @GetMapping("")
    public String showCategory(Model model){
        List<Category> categories=categoryRepository.findAll();
        model.addAttribute("categories",categories);
        return "admin/category";
    }
    @GetMapping("/create")
    public String createCategory(Model model){
        Category category=new Category();
        model.addAttribute("category",category);
        return "admin/createcategory";
}

    @PostMapping("/create")
    public String createNewCategory(@ModelAttribute("category") Category category){
        Category category1=new Category();
        category1.setCategoryName(category.getCategoryName());
        categoryRepository.save(category1);
        return "admin/success";
    }
    @GetMapping("/edit/{id}")
    public String showEditCategory(@PathVariable("id") int categoryId, Model model){
        Category category=categoryRepository.findByCategoryId(categoryId);
        model.addAttribute("category",category);
        return "admin/editcategory";
    }
    @PostMapping("/edit/{id}")
    public String editCategory(@ModelAttribute("category") Category category,@PathVariable("id") int categoryId ){
        Category category1=categoryRepository.findByCategoryId(categoryId);
        category1.setCategoryName(category.getCategoryName());
       categoryRepository.save(category1);
        return "admin/success";
    }
    @GetMapping("/delete/{id}")
    public String deleteCategory(@ModelAttribute("category") Category category,@PathVariable("id") int categoryId){
        Category category1=categoryRepository.findByCategoryId(categoryId);
        categoryRepository.delete(category1);
        return "admin/success";
    }
    @PostMapping("/save-image")
    public String saveImageCustomer(@RequestParam("avatar") MultipartFile file,@RequestParam("categoryId") int categoryId) {
        if (file.isEmpty()) {
            return "error/file-empty";
        }
        User currentUser = customUserDetailsService.getCurrentUser();
Category category=categoryRepository.findByCategoryId(categoryId);

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
            String uploadDir = "static/images/categories/" + category.getCategoryId();
            // Create directory safely
            Path directory = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(directory);
            // Save file
            Path targetLocation = directory.resolve(safeFileName);
            file.transferTo(targetLocation.toFile());
            // Save image URL to user
            String imageUrl = "/images/categories/" + category.getCategoryId() + "/" + safeFileName;
            category.setImage(imageUrl);
            categoryRepository.save(category);

            return "admin/success";
        } catch (IOException e) {
            // Use proper logging
            return "error/upload-failed";
        }
    }

}

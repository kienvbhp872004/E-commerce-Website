package com.hust.seller.admin;

import com.hust.seller.entity.Shop;
import com.hust.seller.entity.User;
import com.hust.seller.repository.ShopRepository;
import com.hust.seller.repository.UserRepository;
import com.hust.seller.security.CustomUserDetailsService;
import com.hust.seller.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminShopController {
    private UserService userService;
    private CustomUserDetailsService customUserDetailsService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ShopRepository shopRepository;

    public AdminShopController(UserService userService, CustomUserDetailsService customUserDetailsService, UserRepository userRepository, PasswordEncoder passwordEncoder, ShopRepository shopRepository) {
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.shopRepository = shopRepository;
    }


    @GetMapping("/shop")
    public String viewShop(Model model) {
        User user=customUserDetailsService.getCurrentUser();
        List<Shop> shopList=shopRepository.findAll();
    model.addAttribute("user",user);
    model.addAttribute("shopList",shopList);
        return "admin/shop";
    }

    @GetMapping("/shop/edit/{id}")
    public String showeditShop(@PathVariable("id") int id, Model model) {
      Optional<Shop> shop1 =shopRepository.findByShopID(id);
      Shop shop=shop1.get();
        model.addAttribute("shop", shop);
        return "admin/editshop";
    }
    @PostMapping("/shop/edit/{id}")
    public String editShop(@PathVariable("id") int id, @ModelAttribute("shop") Shop shop,Model model) {
        shopRepository.save(shop);
        return "admin/success";
    }


}

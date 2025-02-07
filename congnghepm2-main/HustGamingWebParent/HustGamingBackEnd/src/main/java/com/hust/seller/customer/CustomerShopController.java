package com.hust.seller.customer;

import com.hust.seller.repository.ShopRepository;
import com.hust.seller.security.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.hust.seller.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/shop")
@Controller
public class CustomerShopController {
    @Autowired
   private ShopRepository shopRepository;
    @Autowired
    private  CustomUserDetailsService customUserDetailsService;

    public CustomerShopController(ShopRepository shopRepository, CustomUserDetailsService customUserDetailsService) {
        this.shopRepository = shopRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/{id}")
    public String getShop(@PathVariable("id") int id, Model model) {
        List<Product> productList = shopRepository.findAllProductByShopId(id) ;
        model.addAttribute("shop",shopRepository.findShopByShopId(id)) ;
        model.addAttribute("user", customUserDetailsService.getCurrentUser()) ;
        model.addAttribute("productCount",productList.size()) ;
        model.addAttribute("productList",productList) ;
        return "shop" ;
    }
}
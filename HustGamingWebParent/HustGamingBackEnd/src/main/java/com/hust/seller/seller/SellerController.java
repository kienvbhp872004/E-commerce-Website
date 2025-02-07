package com.hust.seller.seller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/seller")
public class SellerController {
        @GetMapping("/")
        public String sellerHome(Model model) {
            // Bạn có thể thêm dữ liệu vào model nếu cần thiết
            model.addAttribute("message", "Welcome to the Seller Home Page!");
            return "seller/home"; // Trả về template customer/home.html
        }



}



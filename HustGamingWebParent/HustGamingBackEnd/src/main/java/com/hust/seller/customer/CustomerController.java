package com.hust.seller.customer;

import com.hust.seller.entity.Product;
import com.hust.seller.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ProductService productService;
    @GetMapping("/")
    public String customerHome(Model model) {
        // Bạn có thể thêm dữ liệu vào model nếu cần thiết
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products",products);
        model.addAttribute("message", "Welcome to the Customer Home Page!");
        return "customer/home"; // Trả về template customer/home.html
    }
}

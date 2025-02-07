package com.hust.seller.seller;

import com.hust.seller.entity.Product;
import com.hust.seller.entity.Review;
import com.hust.seller.entity.Shop;
import com.hust.seller.entity.User;
import com.hust.seller.repository.OrderRepository;
import com.hust.seller.repository.ProductRepository;
import com.hust.seller.repository.ReviewRepository;
import com.hust.seller.repository.ShopRepository;
import com.hust.seller.security.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/seller/reviews")
public class SellerReviewController {
private CustomUserDetailsService customUserDetailsService;
private ShopRepository shopRepository;
private OrderRepository orderRepository;
private  ProductRepository productRepository;
private ReviewRepository reviewRepository;

    public SellerReviewController(CustomUserDetailsService customUserDetailsService, ShopRepository shopRepository, OrderRepository orderRepository, ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.customUserDetailsService = customUserDetailsService;
        this.shopRepository = shopRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("")
    public String viewReviews(Model model){
        User user=customUserDetailsService.getCurrentUser();
        model.addAttribute("user",user);
        Shop shop =shopRepository.findBySellerID(user.getUserID()).get();
        List<Review> reviewList=new ArrayList<>();
        List<Product> productList=productRepository.findByShopID(shop.getShopID());
       for(Product product:productList){
           List<Review> reviews=reviewRepository.findByProductID(product.getProductID());
           reviewList.addAll(reviews);
       }
       model.addAttribute("reviews", reviewList);
    return "seller/review";
        }

}

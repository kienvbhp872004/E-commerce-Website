package com.hust.seller.customer;

import com.hust.seller.entity.Order;
import com.hust.seller.entity.Product;
import com.hust.seller.entity.Review;
import com.hust.seller.repository.OrderRepository;
import com.hust.seller.repository.ProductRepository;
import com.hust.seller.repository.ReviewRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/customer")
public class CustomerCommentController {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;


    public CustomerCommentController(OrderRepository orderRepository, ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;

    }

    @PostMapping("/comment")
    public String addComment(@RequestParam("orderId") int orderId, @RequestParam("comment") String comment, @RequestParam("rating") int rating, Model model){
     Order order=orderRepository.findByOrderID(orderId);
        Review review=new Review();
     review.setProductID(order.getProductID());
     review.setBuyerID(order.getBuyerID());
     review.setRating(rating);
     review.setComment(comment);
     review.setReviewDate(LocalDateTime.now());
     reviewRepository.save(review);
        String status="Ban vua them binh luan thanh cong";
        model.addAttribute("status",status);
    return "customer/statuscheckout";
    }

}

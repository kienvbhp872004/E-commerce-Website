package com.hust.seller.user;

import com.hust.seller.entity.Order;
import com.hust.seller.entity.Product;
import com.hust.seller.entity.User;
import com.hust.seller.repository.OrderRepository;
import com.hust.seller.repository.ProductRepository;
import com.hust.seller.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    public UserService(JavaMailSender mailSender, UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public void sendResetEmail(User user, String token) {
        String recipientEmail = user.getEmail();
        String resetUrl = "http://localhost:8080/reset-password?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Reset mật khẩu của bạn");
        message.setText("Để reset mật khẩu, vui lòng nhấp vào link sau: \n" + resetUrl);
        message.setFrom("quant5ml10@gmail.com");
        // Gửi email
        mailSender.send(message);

        System.out.println("Reset password email đã được gửi tới: " + recipientEmail);
    }
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public String generateResetToken(User user){
        String token = UUID.randomUUID().toString();
        // Lưu token vào database
        user.setToken(token);
        user.setToken_expire(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);
        return token;
    }
    public Optional<User> findByToken(String token){
        return userRepository.findByToken(token);
    }
    public Optional<User> findByUsername(String username){return userRepository.findByUsername(username);}
    public void updatePassword(User user,String password){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String EncoderPassword=passwordEncoder.encode(password);
        user.setPassword(EncoderPassword);
        user.setToken("");
        user.setToken_expire(LocalDateTime.now());
        userRepository.save(user);
    }
    public void sendOrderEmail(User user, Order order) {
        String recipientEmail = user.getEmail();
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Chào ").append(user.getFullName()).append(",\n\n");
        emailContent.append("Cảm ơn bạn đã đặt hàng tại cửa hàng của chúng tôi. Dưới đây là thông tin đơn hàng của bạn:\n\n");
        emailContent.append("Mã đơn hàng: ").append(order.getOrderID()).append("\n");
        emailContent.append("Chi tiết sản phẩm:\n");
            // lay thong tin san pham
        emailContent.append("- ").append(productRepository.findByProductID(order.getProductID()).getProductName());
        emailContent.append(" (Số lượng: ").append(order.getQuantity());
        emailContent.append(", Giá: ").append(order.getPrice());
        emailContent.append(")\n");

        emailContent.append("\nTổng cộng: ").append(String.format("%,d", order.getTotalAmount())).append(" VNĐ\n\n");
        emailContent.append("Chúng tôi sẽ xử lý đơn hàng của bạn trong thời gian sớm nhất. Mọi thắc mắc, vui lòng liên hệ với chúng tôi.\n\n");
        emailContent.append("Trân trọng,\n");
        emailContent.append("Đội ngũ hỗ trợ khách hàng\n");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Xác nhận đơn hàng: " + order.getOrderID());
        message.setText(emailContent.toString());
        message.setFrom("quant5ml10@gmail.com");
        try {
            mailSender.send(message);
            System.out.println("Email thông báo đơn hàng đã được gửi tới: " + recipientEmail);
        } catch (Exception e) {
            System.err.println("Gửi email thất bại: " + e.getMessage());
        }
    }


}







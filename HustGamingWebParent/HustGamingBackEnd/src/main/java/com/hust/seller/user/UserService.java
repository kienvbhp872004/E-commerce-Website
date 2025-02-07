package com.hust.seller.user;

import com.hust.seller.entity.User;
import com.hust.seller.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserRepository userRepository;


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
        user.setToken_expire(LocalDateTime.now().plusMinutes(1));
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



}


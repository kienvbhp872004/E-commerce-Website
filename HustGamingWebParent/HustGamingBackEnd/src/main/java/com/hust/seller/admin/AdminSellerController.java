package com.hust.seller.admin;

import com.hust.seller.entity.User;
import com.hust.seller.security.CustomUserDetailsService;
import com.hust.seller.security.UserRepository;
import com.hust.seller.user.UserDTO;
import com.hust.seller.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/sellers")
public class AdminSellerController {
    private UserRepository userRepository;


    public AdminSellerController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("")
    public String showUsers(Model model) {
        List<User> seller= userRepository.findByRoles_RoleName("ROLE_SELLER");
        model.addAttribute("seller",seller);
        return "admin/seller";
    }
    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") int userId, Model model) {
        Optional<User> userOptional = userRepository.findByUserID(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO();
            // Sao chép dữ liệu từ `User` sang `UserDTO`
            userDTO.setUsername(user.getUsername());
            //khong xu ly set mat khau vi bao mat thong tin
            userDTO.setEmail(user.getEmail());
            userDTO.setFullName(user.getFullName());
            userDTO.setAddress(user.getAddress());
            userDTO.setPhoneNumber(user.getPhoneNumber());
            userDTO.setActive(user.getActive());
            model.addAttribute("userDTO", userDTO);
            model.addAttribute("user", user); // Có thể dùng trong form nếu cần
            return "admin/editseller"; // Tên file HTML chứa form chỉnh sửa
        } else {
            return "redirect:/admin?error=UserNotFound";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") int userId, @ModelAttribute("userDTO") UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByUserID(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
//admin chi khoa tai khoan, khong thay doi thong tin cua seller
            user.setActive(userDTO.getActive());
            userRepository.save(user);
            return "redirect:/admin/";
        } else {
            return "error/emailexsist";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int userId){
        Optional<User> userOptional=userRepository.findByUserID(userId);
       if(userOptional.isPresent()){
           User user=userOptional.get();
           userRepository.delete(user);
           return "/admin/home";
       } else{
           return "error/emailexsist";
        }
    }


}

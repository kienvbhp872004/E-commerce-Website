package com.hust.seller.user;

import com.hust.seller.entity.Cart;
import com.hust.seller.entity.Role;
import com.hust.seller.entity.User;
import com.hust.seller.repository.CartRepository;
import com.hust.seller.repository.RoleRepository;
import com.hust.seller.repository.UserRepository;
import com.hust.seller.security.CustomUserDetailsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@RequestMapping("/")
@Controller
public class RegistrationController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private UserService userService;
    private CartRepository cartRepository;
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    private AuthenticationSuccessHandler successHandler; // Xử lý chuyển hướng sau khi đăng nhập thành công

    public RegistrationController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,UserService userService,CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService=userService;
        this.cartRepository=cartRepository;

    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(UserDTO userDTO) {
        // Kiểm tra nếu email đã tồn tại
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            // Xử lý lỗi nếu email đã tồn tại
            return "error/emailexsist";
        }
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            // Xử lý lỗi nếu username đã tồn tại
            return "error/emailexsist";
        }

        // Tạo đối tượng User mới
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        // Gán vai trò cho người dùng dựa trên lựa chọn
        Role role;
        switch (userDTO.getRole()) {
            case "ROLE_SELLER":
                role = roleRepository.findByRoleName("ROLE_SELLER").orElseThrow();
                break;
//            case "ROLE_ADMIN":
//                role = roleRepository.findByRoleName("ROLE_ADMIN").orElseThrow();
//                break;
            default:
                role = roleRepository.findByRoleName("ROLE_CUSTOMER").orElseThrow();

                break;
        }
        user.setRoles(Set.of(role)); // Gán vai trò cho người dùng

        // Lưu người dùng vào cơ sở dữ liệu
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
       if(role.getRoleName().equals("ROLE_CUSTOMER")){
           int customerID= user.getUserID();
           Cart cart=new Cart();
           cart.setCustomerID(customerID);
           cartRepository.save(cart);
       }
       System.out.println(role.getRoleName());

        return "redirect:/login?success"; // Sau khi đăng ký, điều hướng sang trang đăng nhập
    }
    @PostMapping("/login")
    public String loginPost(
            @RequestParam String username,
            @RequestParam String password,
            Model model
           ) {
        try {
            // Tạo đối tượng xác thực
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            // Xác thực người dùng
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // Lưu thông tin xác thực vào SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Điều hướng theo vai trò của người dùng
            successHandler.onAuthenticationSuccess(null, null, authentication);
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Lấy thông tin xác thực từ SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            // Hủy bỏ xác thực (xóa SecurityContext)
            SecurityContextHolder.clearContext();
            // Invalidate session
            request.getSession().invalidate();
            // Xóa cookie JSESSIONID và remember-me
            Cookie jsessionidCookie = new Cookie("JSESSIONID", null);
            jsessionidCookie.setPath("/");
            jsessionidCookie.setMaxAge(0);
            response.addCookie(jsessionidCookie);
        }
        // Chuyển hướng về trang đăng nhập sau khi đăng xuất thành công
        return "redirect:/login?logout";
    }
    @GetMapping("/forgot-password")
    public String showForgot_password(){
      return "forgot-password";
    }
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email,Model model){
        Optional<User> userOptional=userService.findByEmail(email);
        if(userOptional.isPresent()){
            User user=userOptional.get();
            String token =userService.generateResetToken(user);
            userService.sendResetEmail(user,token);
            model.addAttribute("success","da gui max xac thuc ve email");
        }else{
            model.addAttribute("error","email khong ton tai");
        }
        return "forgot-password";
    }
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        Optional<User> userOptional = userService.findByToken(token);
        if (!userOptional.isPresent()) {
            model.addAttribute("error", "Token không hợp lệ hoặc đã hết hạn.");
            return "reset-password"; // Trả về trang reset-password với thông báo lỗi
        }
        model.addAttribute("token", token); // Thêm token vào model để gửi tới form
        return "reset-password"; // Trả về trang reset mật khẩu
    }
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token")String token,@RequestParam("password") String password,@RequestParam("confirmPassword") String confirmpassword,Model model){
        // Tìm người dùng dựa trên token
        Optional<User> userOptional = userService.findByToken(token);

        // Kiểm tra nếu token không hợp lệ hoặc người dùng không tồn tại
        if (!userOptional.isPresent()) {
            model.addAttribute("error", "Token không hợp lệ");
            return "reset-password"; // Trả về trang reset password với thông báo lỗi
        }
        User user =userOptional.get();
        if(user.getToken_expire().isBefore(LocalDateTime.now())){
            model.addAttribute("error","token da het han");
            return "reset-password";
        }

        // Kiểm tra nếu mật khẩu và xác nhận mật khẩu không khớp
        if (!password.equals(confirmpassword)) {
            model.addAttribute("error", "Mật khẩu không khớp.");
            return "reset-password"; // Trả về trang reset password với thông báo lỗi
        }
        // Đặt lại mật khẩu cho người dùng
        userService.updatePassword(user, password);
        model.addAttribute("message", "Đặt lại mật khẩu thành công.");
        return "login"; // Chuyển hướng về trang đăng nhập sau khi đặt lại mật khẩu thành công
    }


}






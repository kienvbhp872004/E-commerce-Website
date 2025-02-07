package com.hust.seller.customer;

import com.hust.seller.entity.*;
import com.hust.seller.product.ProductDTO;
import com.hust.seller.repository.*;
import com.hust.seller.security.CustomUserDetailsService;
import com.hust.seller.user.UserService;
import org.hibernate.boot.model.source.spi.Orderable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerCheckoutController {
    private CustomUserDetailsService customUserDetailsService;
    private CartItemRepository cartItemRepository;
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private ShopRepository shopRepository;
    private CategoryRepository categoryRepository;
    private OrderRepository orderRepository;
    private UserService userService;


    public CustomerCheckoutController(CustomUserDetailsService customUserDetailsService, CartItemRepository cartItemRepository, CartRepository cartRepository, ProductRepository productRepository, ShopRepository shopRepository, CategoryRepository categoryRepository,OrderRepository orderRepository,UserService userService) {
        this.customUserDetailsService = customUserDetailsService;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
        this.categoryRepository = categoryRepository;
        this.orderRepository=orderRepository;
        this.userService=userService;
    }
    @GetMapping("/checkout")
    public String viewCheckout(Model model){
        User user=customUserDetailsService.getCurrentUser();
        Cart cart=cartRepository.findByCustomerID(user.getUserID());
        List<CartItems> cartItemsList=cartItemRepository.findByCartID(cart.getCartID());
        List<ProductDTO> productDTOS=new ArrayList<>();
        int totalmoney=0;
        int totalproduct=0;
        for(CartItems item:cartItemsList) {
            ProductDTO productDTO = new ProductDTO();
            Product product=productRepository.findByProductID(item.getProductID());
            productDTO.setProduct(product);
            Optional<Shop> shopOptional =shopRepository.findByShopID(product.getShopID());
            Shop shop=shopOptional.get();
            productDTO.setShop(shop);
            productDTO.setCategoryName(categoryRepository.findByCategoryId(product.getCategoryID()).getCategoryName());
            productDTO.setQuantity(item.getQuantity());
            productDTO.setTotal(product.getPrice()* item.getQuantity());
            productDTOS.add(productDTO);
            totalmoney = totalmoney + productDTO.getTotal();// tong gia tri gio hang
            totalproduct+=1;
        }
        model.addAttribute("productDTOs",productDTOS);
        model.addAttribute("totalmoney",totalmoney);
        model.addAttribute("totalproduct",totalproduct);
        model.addAttribute("user", user);
    return "checkout";
    }
    @PostMapping("/checkout")
    public String Checkout(Model model){
        User user=customUserDetailsService.getCurrentUser();
        Cart cart=cartRepository.findByCustomerID(user.getUserID());
        List<CartItems> cartItemsList=cartItemRepository.findByCartID(cart.getCartID());
        String status;
        for(CartItems item:cartItemsList){
            // kiem tra shop co hoat dong
            Product product=productRepository.findByProductID(item.getProductID());
            Optional<Shop> shop1=shopRepository.findByShopID(product.getShopID());
            Shop shop =shop1.get();
            if(product.getQuantity()<item.getQuantity()) {
                status="Sản phẩm "+product.getProductName()+" có số lượng "+product.getQuantity()+", vui lòng điều chỉnh lại số lượng sản phẩm trong giỏ" +"("+"vượt quá "+(item.getQuantity()-product.getQuantity())+"  sản phẩm)";
                model.addAttribute("status",status);
                model.addAttribute("user",user);
                return "customer/statuscheckout";
            }
            if(product.isStatus()==false) {
                status = "Sản phẩm " + product.getProductName() + " đang trong trạng thái đóng, vui lòng xoá sản phẩm này khỏi giỏ hàng";
                model.addAttribute("status", status);
                model.addAttribute("user",user);
                return "customer/statuscheckout";

            }
            if(shop.isStatus()==false){
                status="Shop "+shop.getName()+" đang đóng cửa, vui lòng xoá các sản phẩm của shop này và chọn các sản phẩm của shop khác";
                model.addAttribute("status", status);
                model.addAttribute("user",user);
                return "customer/statuscheckout";
            }
        }
        for(CartItems item:cartItemsList){
            Product product=productRepository.findByProductID(item.getProductID());
            Order order=new Order();
            order.setBuyerID(user.getUserID());
            order.setShopID(product.getShopID());
            order.setProductID(product.getProductID());
            order.setQuantity(item.getQuantity());
            order.setPrice(product.getPrice());
            order.setTotalAmount(item.getQuantity()*product.getPrice());
            order.setShippingAddress(user.getAddress());
            order.setPayment("COD");
            order.setOrderDate(LocalDateTime.now());
            order.setStatus(1);
            orderRepository.save(order);
            // cap nhat lai so luong san pham
            product.setQuantity(product.getQuantity()- item.getQuantity());
            productRepository.save(product);
            // gui email thong bao
            userService.sendOrderEmail(user, order);

        }
        cartItemRepository.deleteByCartID(cart.getCartID());
        status="Bạn vừa đặt hàng thành công, vui lòng theo dõi trạng thái của các đơn hàng vừa đặt. Xin cảm ơn.";
        model.addAttribute("status",status);
        model.addAttribute("user",user);
        return "customer/statuscheckout";
    }
    @GetMapping("/statuscheckout")
    public String viewStatuscheckout(){
        return "customer/statuscheckout";
    }





}

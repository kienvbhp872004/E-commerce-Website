package com.hust.seller.seller;
import com.hust.seller.entity.*;
import com.hust.seller.order.OrderDTO;
import com.hust.seller.repository.*;
import com.hust.seller.security.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/seller/")
    public class SellerOrderController{
        private CustomUserDetailsService customUserDetailsService;
        private OrderRepository orderRepository;
        private OrderStatusRepository orderStatusRepository;
        private ProductRepository productRepository;
        private CategoryRepository categoryRepository;
        private ShopRepository shopRepository;

        public SellerOrderController(CustomUserDetailsService customUserDetailsService, OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, ProductRepository productRepository, CategoryRepository categoryRepository, ShopRepository shopRepository) {
            this.customUserDetailsService = customUserDetailsService;
            this.orderRepository = orderRepository;
            this.orderStatusRepository = orderStatusRepository;
            this.productRepository = productRepository;
            this.categoryRepository = categoryRepository;
            this.shopRepository = shopRepository;
        }

        @GetMapping("/orders")
        public String viewOrder(Model model) {
            User user = customUserDetailsService.getCurrentUser();
            model.addAttribute("user", user);
            // tim shop cua nguoi ban hang do
            Optional<Shop> shop1=shopRepository.findBySellerID(user.getUserID());
            List<Order> orders=orderRepository.findByShopID(shop1.get().getShopID());

            List <OrderDTO> orderDTOS=new ArrayList<>();
            for(Order order:orders){
                OrderDTO orderDTO=new OrderDTO();
                orderDTO.setOrder(order);
                orderDTO.setStatus_name(orderStatusRepository.findByStatusID(order.getStatus()).getStatus_name());
                orderDTOS.add(orderDTO);
            }
            model.addAttribute("orders", orderDTOS);
            return "seller/order";
        }

        @GetMapping("/orders/{id}")
        public String viewOrderDetail(@PathVariable("id") int id, Model model) {
            Order order = orderRepository.findByOrderID(id);
            model.addAttribute("order",order);
            User user=customUserDetailsService.getCurrentUser();
            model.addAttribute("user",user);
            Product product =productRepository.findByProductID(order.getProductID());
            model.addAttribute("product",product);
            Category category=categoryRepository.findByCategoryId(product.getCategoryID());
            model.addAttribute("category",category);
            Optional<Shop> shop1=shopRepository.findByShopID(product.getShopID());
            Shop shop=shop1.get();
            model.addAttribute("shop",shop);
            OrderStatus orderStatus=orderStatusRepository.findByStatusID(order.getStatus());
            model.addAttribute("orderstatus",orderStatus.getStatus_name());
            return "seller/orderdetail";
        }
        @PostMapping("/update-order-status")
        public String editStatusOrder(@RequestParam("orderId") int orderId , @RequestParam("status") int status, Model model, @RequestHeader(value = "Referer", required = false) String referer){
            Order order=orderRepository.findByOrderID(orderId);
            order.setStatus(status);
            orderRepository.save(order);
            return referer != null ? "redirect:" + referer : "redirect:/customer/orders/" + orderId;
        }



    }



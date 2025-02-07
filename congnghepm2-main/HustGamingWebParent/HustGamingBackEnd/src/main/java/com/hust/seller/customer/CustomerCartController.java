package com.hust.seller.customer;

import com.hust.seller.entity.*;
import com.hust.seller.product.ProductDTO;
import com.hust.seller.repository.*;
import com.hust.seller.security.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerCartController {

    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private CustomUserDetailsService customUserDetailsService;
    private ProductRepository productRepository;
    private ShopRepository shopRepository;
    private CategoryRepository categoryRepository;


    public CustomerCartController(CartRepository cartRepository, CartItemRepository cartItemRepository, CustomUserDetailsService customUserDetailsService,ProductRepository productRepository,ShopRepository shopRepository,CategoryRepository categoryRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.productRepository=productRepository;
        this.shopRepository=shopRepository;
        this.categoryRepository=categoryRepository;
    }

    @GetMapping("/cart")
    public String viewCart(Model model){
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
        model.addAttribute("user",user);
        return "cart";
    }
    @PostMapping("/cart/add/{id}")//id la ma san pham, them san pham vao gio
    public String addtoCart(@PathVariable("id") int id, @RequestParam("quantity")int quantity, HttpServletRequest request){
        User user = customUserDetailsService.getCurrentUser();
        Cart cart=cartRepository.findByCustomerID(user.getUserID());
        List<CartItems> cartItemsList=cartItemRepository.findByCartID(cart.getCartID());
       CartItems cartItems=null;
       for(CartItems cartItems1:cartItemsList){
           if(cartItems1.getProductID()==id) cartItems=cartItems1;
       }
        // xu ly logic
        if (cartItems!=null) {// co ton tai san pham do
            cartItems.setQuantity(cartItems.getQuantity()+quantity);
            cartItemRepository.save(cartItems);
        }else{
            CartItems cartItems1=new CartItems(cart.getCartID(), id,quantity);
            cartItemRepository.save(cartItems1);
        }
        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

    @PostMapping("/cart/update")
    public String UpdateProduct(HttpServletRequest request,@RequestParam("productId")int productId,@RequestParam("quantity")int quantity){
        User user = customUserDetailsService.getCurrentUser();
        Cart cart=cartRepository.findByCustomerID(user.getUserID());
        List<CartItems> cartItemsList=cartItemRepository.findByCartID(cart.getCartID());
        CartItems cartItems=null;
        for(CartItems cartItems1:cartItemsList){
            if(cartItems1.getProductID()==productId) cartItems=cartItems1;
        }
        cartItems.setQuantity(quantity);
        cartItemRepository.save(cartItems);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
     @PostMapping("cart/delete")
    public String deleteProduct(HttpServletRequest request,@RequestParam("productId") int productId){
         User user = customUserDetailsService.getCurrentUser();
         Cart cart=cartRepository.findByCustomerID(user.getUserID());
         List<CartItems> cartItemsList=cartItemRepository.findByCartID(cart.getCartID());
         CartItems cartItems=null;
         for(CartItems cartItems1:cartItemsList){
             if(cartItems1.getProductID()==productId) cartItems=cartItems1;
         }
         cartItemRepository.delete(cartItems);
       String referer = request.getHeader("Referer");
         return "redirect:" + referer;
     }

}

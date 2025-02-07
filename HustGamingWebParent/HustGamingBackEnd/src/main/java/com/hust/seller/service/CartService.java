package com.hust.seller.service;

import com.hust.seller.entity.Cart;
import com.hust.seller.entity.CartItems;
import com.hust.seller.entity.Product;
import com.hust.seller.repository.CartItemsRepository;
import com.hust.seller.repository.CartRepository;
import com.hust.seller.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository ;
    @Autowired
    CartItemsRepository cartItemsRepository ;

    public List<CartItems> getAllCartItemsWithCartID(int cartID) {
        return cartItemsRepository.findAllByCartID(cartID);
    }

    public Cart getCartWithCustomerID(int customerID) {
        return cartRepository.findByCustomerID(customerID) ;
    }
    public Product getProductWithProductID(int productID) {
        return cartItemsRepository.findCartItemsByProductID(productID) ;
    }


}

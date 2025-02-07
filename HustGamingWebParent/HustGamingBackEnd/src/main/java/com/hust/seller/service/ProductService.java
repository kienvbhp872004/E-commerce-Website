package com.hust.seller.service;

import com.hust.seller.entity.CartItems;
import com.hust.seller.entity.Product;
import com.hust.seller.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductByProductID(int productID) {
        return productRepository.findByProductID(productID) ;
    }

}

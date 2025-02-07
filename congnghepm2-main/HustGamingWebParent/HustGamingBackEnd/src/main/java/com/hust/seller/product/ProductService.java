package com.hust.seller.product;

import com.hust.seller.entity.ImageProduct;
import com.hust.seller.entity.Product;
import com.hust.seller.repository.ImageProductRepository;
import com.hust.seller.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    ImageProductRepository imageProductRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ImageProductRepository imageProductRepository, ProductRepository productRepository) {
        this.imageProductRepository = imageProductRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void deleteAllProductImages(int productId) {
        imageProductRepository.deleteByProductId(productId);
    }

    public void saveProductImage(int productId, String image) {
        // Tạo đối tượng ProductImage
        ImageProduct imageProduct = new ImageProduct();
        imageProduct.setProductID(productId);  // Liên kết với ID sản phẩm
        imageProduct.setImage(image);    // Lưu đường dẫn của ảnh

        // Lưu ProductImage vào database
        imageProductRepository.save(imageProduct);
    }

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    public Product findByProductID(int productID) {
        return this.productRepository.findByProductID(productID);
    }

    public List<Product> findByShopID(int shopID) {
        return this.productRepository.findByShopID(shopID);
    }

    public List<Product> searchAndSortProducts(String keyword, String sortField, BigDecimal minPrice, BigDecimal maxPrice) {
        return this.productRepository.searchAndSortProducts(keyword, sortField, minPrice, maxPrice);
    }

    public Page<Product> getAllProducts(Integer page) {
        Pageable pageable = PageRequest.of(page - 1, 20);
        return this.productRepository.findAll(pageable);
    }

    public Page<Product> searchAndSortProducts(String keyword, String sortField, BigDecimal minPrice, BigDecimal maxPrice, Integer page) {
        List<Product> allResults = this.searchAndSortProducts(keyword, sortField, minPrice, maxPrice);
        int totalSize = allResults.size();
        Pageable pageable = PageRequest.of(page - 1, 20);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), totalSize);
        List<Product> pageContent = allResults.subList(start, end);
        return new PageImpl<>(pageContent, pageable, totalSize);
    }
}
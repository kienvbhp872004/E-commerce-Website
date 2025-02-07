package com.hust.seller.seller;
import com.hust.seller.entity.*;
import com.hust.seller.product.ProductService;
import com.hust.seller.repository.CategoryRepository;
import com.hust.seller.repository.ImageProductRepository;
import com.hust.seller.repository.ProductRepository;
import com.hust.seller.repository.ShopRepository;
import com.hust.seller.security.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/seller/products")
public class SellerProductController {
    private ProductRepository productRepository;
    private ShopRepository shopRepository;
    private CustomUserDetailsService customUserDetailsService;
    private CategoryRepository categoryRepository;
    private ImageProductRepository imageProductRepository;
    private ProductService productService;

    public SellerProductController(ProductRepository productRepository, ShopRepository shopRepository, CustomUserDetailsService customUserDetailsService, CategoryRepository categoryRepository, ImageProductRepository imageProductRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.categoryRepository = categoryRepository;
        this.imageProductRepository = imageProductRepository;
        this.productService = productService;
    }

    @GetMapping("")
    public String showProduct(Model model) {
        User currentUser = customUserDetailsService.getCurrentUser();
        Optional<Shop> shop1 = shopRepository.findBySellerID(currentUser.getUserID());
        if (shop1.isPresent()) {
            Shop shop = shop1.get();
            List<Product> productList = productRepository.findByShopID(shop.getShopID());
            model.addAttribute("products", productList);
            return "seller/product";
        } else {
            return "seller/fairlure";
        }
    }

    @GetMapping("/create")
    public String showCreateProduct(Model model) {
        Product product = new Product();
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        System.out.println(categories);
        return "seller/createproduct";
    }

    @PostMapping("/create")
    @Transactional
    public String createProduct(
            @ModelAttribute("product") Product product,
            @RequestParam("productImages") List<MultipartFile> files,
            Model model) {
        User currentUser = customUserDetailsService.getCurrentUser();
        Optional<Shop> shop1 = shopRepository.findBySellerID(currentUser.getUserID());
        Shop shop = shop1.get();
        product.setShopID(shop.getShopID());
        productRepository.save(product);
        Integer productId = product.getProductID();
        String rootDir = System.getProperty("user.dir");
        //su dung duong dan tuyet doi de luu file, con luu vao database thi luu duong dan tuong doi
        String uploadDirPath = rootDir + "/static/images/product/" + productId + "/";
        File uploadPath = new File(uploadDirPath);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        try {
            int i=1;
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                    Path filePath = Paths.get(uploadDirPath, fileName);
                    Files.write(filePath, file.getBytes());
                    ImageProduct imageProduct = new ImageProduct();
                    imageProduct.setProductID(productId);
                    imageProduct.setImage("/images/product/" + productId + "/" + fileName);
                    imageProductRepository.save(imageProduct);
                    if(i==1){
                        product.setImage("/images/product/" + productId + "/" + fileName);
                        i++;
                    }
                }
            }
            return "seller/success";
        } catch (IOException e) {
            model.addAttribute("message", "Failed to upload images.");
            e.printStackTrace();
        }
        return "productForm";
    }

    @GetMapping("/edit/{id}")
    public String showEditProduct(@PathVariable("id") int id, Model model) {
        Product product = productRepository.findByProductID(id);
        List<ImageProduct> productImages = imageProductRepository.findByProductID(id);
        model.addAttribute("productImages", productImages);
        model.addAttribute("product", product);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "seller/editproduct";
    }

    @PostMapping("/edit/{id}")
    @Transactional
    public String updateProductImages(@PathVariable("id") int productId, @RequestParam("productImages") List<MultipartFile> productImages, RedirectAttributes redirectAttributes, @ModelAttribute("product") Product product) {

        String rootDir = System.getProperty("user.dir");
        String uploadDirPath = rootDir + "/static/images/product/" + productId + "/";
        File dir = new File(uploadDirPath);
//kiem tra xem co thu mux hay chua, neu khong coi thi tao moi thu muc de luu tru anh
        if (!dir.exists()) dir.mkdirs();
// xoa toan bo anh cu
        if (dir.isDirectory() && dir.listFiles() != null) {
            for (File file : dir.listFiles()) {
                if (!file.isDirectory()) {
                    file.delete();
                }
            }
        }
// xoa toan bo anh cu trong database
        productService.deleteAllProductImages(productId);
        int i=1;
        for (MultipartFile image : productImages) {
            if (!image.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(uploadDirPath, fileName);
                try {
                    image.transferTo(filePath.toFile());
                    productService.saveProductImage(productId, "/images/product/" + productId + "/" + fileName);
                    if(i==1){
                        product.setImage("/images/product/" + productId + "/" + fileName);
                        i++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("message", "Failed to upload image: " + fileName);
                    return "redirect:/seller/products/edit/" + productId;
                }
            }
        }

        redirectAttributes.addFlashAttribute("message", "Product images updated successfully!");
        productRepository.save(product);
        return "redirect:/seller/products/edit/" + productId;
    }
}
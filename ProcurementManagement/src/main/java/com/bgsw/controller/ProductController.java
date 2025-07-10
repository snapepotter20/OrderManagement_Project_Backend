package com.bgsw.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bgsw.entity.Product;
import com.bgsw.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getallproducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}

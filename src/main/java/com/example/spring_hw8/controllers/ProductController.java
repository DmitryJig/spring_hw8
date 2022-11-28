package com.example.spring_hw8.controllers;

import com.example.spring_hw8.model.Product;
import com.example.spring_hw8.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> findAllProducts(
            @RequestParam(name = "min_price", defaultValue = "0") Double minPrice,
            @RequestParam(name = "max_price", required = false) Double maxPrice
    ) {
        if (maxPrice == null) {
            maxPrice = Double.MAX_VALUE;
        }
        return productService.findAllByPrice(minPrice, maxPrice);
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id) {
        return productService.findProductById(id).orElseThrow(RuntimeException::new);
    }

    @DeleteMapping
    public void deleteProductById(@RequestParam Long id) {
        productService.deleteProductById(id);
    }

    @PostMapping
    public Product saveNewProduct(@RequestBody Product product) {
        product.setId(null);
        return productService.saveOrUpdate(product);
    }
}

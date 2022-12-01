package com.example.spring_hw8.controllers;

import com.example.spring_hw8.dto.ProductDto;
import com.example.spring_hw8.model.Product;
import com.example.spring_hw8.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    //http://localhost:8189/app/api/v1/products?p=1&min_price=1000&max_price=2000
    @GetMapping
    public Page<ProductDto> findAllProducts(
            @RequestParam(name = "pageIndex", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Double minCost,
            @RequestParam(name = "max_price", required = false) Double maxCost,
            @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        if (page < 1){
            page = 1;
        }
        return productService.find(minCost, maxCost, titlePart, page).map(p-> new ProductDto(p));
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id) {
        return productService.findProductById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public Product saveNewProduct(@RequestBody ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setCost(productDto.getCost());
//        product.setId(null);
        return productService.save(product);
    }

    // принимаем ДТО и ищем по его id продукт в базе данных, присваиваем ему имя и цену и снова сохраняем в БД
    @PutMapping
    public Product updateProduct(@RequestBody ProductDto productDto){
        Product product = productService.findProductById(productDto.getId()).get();
        product.setCost(productDto.getCost());
        product.setTitle(productDto.getTitle());
        return productService.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}

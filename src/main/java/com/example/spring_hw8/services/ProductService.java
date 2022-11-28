package com.example.spring_hw8.services;

import com.example.spring_hw8.model.Product;
import com.example.spring_hw8.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Optional<Product> findProductById(Long id){
        return productRepository.findById(id);
    }

    public List<Product> findAllByPrice(Double minPrice, Double maxPrice){
        return productRepository.findAllByCostBetween(minPrice, maxPrice);
    }

    public Product saveOrUpdate(Product product){
        return productRepository.save(product);
    }

    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }
}

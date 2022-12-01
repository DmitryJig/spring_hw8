package com.example.spring_hw8.services;

import com.example.spring_hw8.model.Product;
import com.example.spring_hw8.repositories.ProductRepository;
import com.example.spring_hw8.repositories.specifications.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> find(Double minCost, Double maxCost, String partTitle, Integer page){
        Specification<Product> specification = Specification.where(null); // null значит спецификация ничего не проверяет
        if (minCost != null){
            specification = specification.and(ProductSpecifications.costGreaterOrEqualsThan(minCost));
        }
        if (maxCost != null){
            specification = specification.and(ProductSpecifications.costLessOrEqualsThan(maxCost));
        }
        if (partTitle != null){
            specification = specification.and(ProductSpecifications.titleLike(partTitle));
        }
        return productRepository.findAll(specification, PageRequest.of(page - 1, 5)); // without sort
//        return productRepository.findAll(specification, PageRequest.of(page - 1, 5), Sort.by(...));
    }

    public Optional<Product> findProductById(Long id){
        return productRepository.findById(id);
    }

    public List<Product> findAllByPrice(Double minPrice, Double maxPrice){
        return productRepository.findAllByCostBetween(minPrice, maxPrice);
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }
}

package com.example.spring_hw8.dto;

import com.example.spring_hw8.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

    private Long id;

    private String title;

    private Double cost;

    public ProductDto(Product product){
        this.id = product.getId();
        this.title = product.getTitle();
        this.cost = product.getCost();
    }
}

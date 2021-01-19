package com.sda.productionproject.service;

import com.sda.productionproject.dto.ProductDto;
import com.sda.productionproject.model.Product;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface ProductService {
    ProductDto save(ProductDto dto);
    Set<ProductDto> findAll();
    ProductDto findById(Long id);
    ProductDto update(Long id, ProductDto dto);
    void deleteById(Long id);
    Set<ProductDto> productsWithoutMaterials();
    void assignMaterials(Set<ProductDto> productDtos);
    Page<ProductDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}

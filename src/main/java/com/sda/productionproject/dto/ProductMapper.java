package com.sda.productionproject.dto;

import com.sda.productionproject.model.Material;
import com.sda.productionproject.model.Product;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductMapper {

    private final MaterialMapper materialMapper;

    public ProductMapper(MaterialMapper materialMapper) {
        this.materialMapper = materialMapper;
    }

    public Product toEntity(ProductDto dto) {
        if (dto == null) {
            return null;
        }
        final Product entity = new Product();
        entity.setProductId(dto.getProductId());
        entity.setModel(dto.getModel());
        entity.setNumberOfSeats(dto.getNumberOfSeats());
        entity.setBackside(dto.getBackside());
        entity.setConfort(dto.getConfort());
        entity.setRelax(dto.getRelax());
        entity.setLeg(dto.getLeg());
        for (Map.Entry<MaterialDto, Double> element : dto.getMaterials().entrySet()) {
            entity.getMaterials().put(materialMapper.toEntity(element.getKey()), element.getValue());
        }

        return entity;
    }

    public Product toEntity(Product productToUpdate, ProductDto updateDto) {
        productToUpdate.setProductId(updateDto.getProductId());
        productToUpdate.setModel(updateDto.getModel());
        productToUpdate.setNumberOfSeats(updateDto.getNumberOfSeats());
        productToUpdate.setBackside(updateDto.getBackside());
        productToUpdate.setConfort(updateDto.getConfort());
        productToUpdate.setRelax(updateDto.getRelax());
        productToUpdate.setLeg(updateDto.getLeg());
        for (Map.Entry<MaterialDto, Double> element : updateDto.getMaterials().entrySet()) {
            productToUpdate.getMaterials().put(materialMapper.toEntity(element.getKey()), element.getValue());
        }

        return productToUpdate;
    }

    public ProductDto toDto(Product entity) {
        if (entity == null) {
            return null;
        }
        final ProductDto dto = new ProductDto();
        dto.setProductId(entity.getProductId());
        dto.setModel(entity.getModel());
        dto.setNumberOfSeats(entity.getNumberOfSeats());
        dto.setBackside(entity.getBackside());
        dto.setConfort(entity.getConfort());
        dto.setRelax(entity.getRelax());
        dto.setLeg(entity.getLeg());
        for (Map.Entry<Material, Double> element : entity.getMaterials().entrySet()) {
            dto.getMaterials().put(materialMapper.toDto(element.getKey()), element.getValue());
        }

        String text = entity.getModel() + " "
                + entity.getNumberOfSeats() + " "
                + entity.getBackside() + " "
                + entity.getConfort() + " "
                + entity.getRelax() + " "
                + entity.getLeg();
        dto.setDescription(text.replaceAll("null", "").trim());

        return dto;
    }
}

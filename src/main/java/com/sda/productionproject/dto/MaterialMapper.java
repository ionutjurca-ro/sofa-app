package com.sda.productionproject.dto;

import com.sda.productionproject.model.Material;
import org.springframework.stereotype.Component;

@Component
public class MaterialMapper {

    public Material toEntity(MaterialDto dto) {
        if (dto == null) {
            return null;
        }
        final Material entity = new Material();
        entity.setMaterialId(dto.getMaterialId());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    public Material toEntity(Material materialToUpdate, MaterialDto updateDto) {
        materialToUpdate.setMaterialId(updateDto.getMaterialId());
        materialToUpdate.setName(updateDto.getName());
        materialToUpdate.setPrice(updateDto.getPrice());
        return materialToUpdate;
    }

    public MaterialDto toDto(Material entity) {
        if (entity == null) {
            return null;
        }
        final MaterialDto dto = new MaterialDto();
        dto.setMaterialId(entity.getMaterialId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        return dto;
    }
}

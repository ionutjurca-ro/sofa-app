package com.sda.productionproject.dto;

import com.sda.productionproject.model.ArticleList;
import com.sda.productionproject.model.Material;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ArticleListMapper {

    private final MaterialMapper materialMapper;

    public ArticleListMapper(MaterialMapper materialMapper) {
        this.materialMapper = materialMapper;
    }

    public ArticleList toEntity(ArticleListDto dto) {
        if (dto == null) {
            return null;
        }
        final ArticleList entity = new ArticleList();
        entity.setArticleListId(dto.getArticleListId());
        entity.setModel(dto.getModel());
        entity.setNumberOfSeats(dto.getNumberOfSeats());
        entity.setBackside(dto.getBackside());
        entity.setConfort(dto.getConfort());
        entity.setRelax(dto.getRelax());
        entity.setLeg(dto.getLeg());
        entity.setMaterialType(dto.getMaterialType());
        if (!dto.getMaterials().isEmpty()) {
            for (Map.Entry<MaterialDto, Double> element : dto.getMaterials().entrySet()) {
                entity.getMaterials().put(materialMapper.toEntity(element.getKey()), element.getValue());
            }
        }
        return entity;
    }

    public ArticleList toEntity(ArticleList articleListToUpdate, ArticleListDto updateDto) {
        articleListToUpdate.setArticleListId(updateDto.getArticleListId());
        articleListToUpdate.setModel(updateDto.getModel());
        articleListToUpdate.setNumberOfSeats(updateDto.getNumberOfSeats());
        articleListToUpdate.setBackside(updateDto.getBackside());
        articleListToUpdate.setConfort(updateDto.getConfort());
        articleListToUpdate.setRelax(updateDto.getRelax());
        articleListToUpdate.setLeg(updateDto.getLeg());
        articleListToUpdate.setMaterialType(updateDto.getMaterialType());
        if (!updateDto.getMaterials().isEmpty()) {
            for (Map.Entry<MaterialDto, Double> element : updateDto.getMaterials().entrySet()) {
                articleListToUpdate.getMaterials().put(materialMapper.toEntity(element.getKey()), element.getValue());
            }
        }
        return articleListToUpdate;
    }

    public ArticleListDto toDto(ArticleList entity) {
        if (entity == null) {
            return null;
        }
        final ArticleListDto dto = new ArticleListDto();
        dto.setArticleListId(entity.getArticleListId());
        dto.setModel(entity.getModel());
        dto.setNumberOfSeats(entity.getNumberOfSeats());
        dto.setBackside(entity.getBackside());
        dto.setConfort(entity.getConfort());
        dto.setRelax(entity.getRelax());
        dto.setLeg(entity.getLeg());
        dto.setMaterialType(entity.getMaterialType());
        if (!entity.getMaterials().isEmpty()) {
            for (Map.Entry<Material, Double> element : entity.getMaterials().entrySet()) {
                dto.getMaterials().put(materialMapper.toDto(element.getKey()), element.getValue());
            }
        }
        return dto;
    }
}

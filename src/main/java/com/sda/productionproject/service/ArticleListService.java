package com.sda.productionproject.service;

import com.sda.productionproject.dto.ArticleListDto;
import com.sda.productionproject.dto.MaterialDto;
import com.sda.productionproject.model.Backside;
import com.sda.productionproject.model.Confort;
import com.sda.productionproject.model.Leg;
import com.sda.productionproject.model.Relax;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface ArticleListService {
    ArticleListDto save(ArticleListDto dto);
    Set<ArticleListDto> findAll();
    ArticleListDto findById(Long id);
//    ArticleListDto update(Long id, ArticleListDto dto);
    void deleteById(Long id);
    ArticleListDto findByWood(String model, String numberOfSeats, Backside backside);
    ArticleListDto findByHardware(String model, String numberOfSeats, Relax relax);
    ArticleListDto findByPacking(String numberOfSeats);
    ArticleListDto findByLeg(String model, String numberOfSeats, Leg leg);
    ArticleListDto findByPolyether(String model, String numberOfSeats, Confort confort);
    ArticleListDto findByAuxiliary(String model, String numberOfSeats);
    Page<ArticleListDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}

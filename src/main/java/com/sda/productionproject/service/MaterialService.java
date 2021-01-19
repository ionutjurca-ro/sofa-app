package com.sda.productionproject.service;

import com.sda.productionproject.dto.MaterialDto;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface MaterialService {
    MaterialDto save(MaterialDto dto);
    Set<MaterialDto> findAll();
    MaterialDto findById(Long id);
//    MaterialDto update(Long id, MaterialDto dto);
    void deleteById(Long id);
    Page<MaterialDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}

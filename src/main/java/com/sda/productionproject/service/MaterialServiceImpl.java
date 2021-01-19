package com.sda.productionproject.service;

import com.sda.productionproject.dto.MaterialDto;
import com.sda.productionproject.dto.MaterialMapper;
import com.sda.productionproject.model.Material;
import com.sda.productionproject.repository.MaterialRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class MaterialServiceImpl implements MaterialService {

    private MaterialRepository materialRepository;
    private MaterialMapper materialMapper;

    public MaterialServiceImpl(MaterialRepository materialRepository, MaterialMapper materialMapper) {
        this.materialRepository = materialRepository;
        this.materialMapper = materialMapper;
    }

    @Override
    @Transactional
    public MaterialDto save(MaterialDto dto) {
        Material savedMaterial = materialRepository.save(materialMapper.toEntity(dto));
        return materialMapper.toDto(savedMaterial);
    }

    @Override
    public Set<MaterialDto> findAll() {
        Set<MaterialDto> materialDtos = new HashSet<>();
        materialRepository.findAll().forEach(m -> materialDtos.add(materialMapper.toDto(m)));
        return materialDtos;
    }

    @Override
    public MaterialDto findById(Long id) {
        Material foundMaterial = materialRepository.findById(id).orElseThrow(() -> new RuntimeException("material with id " + id.toString() + " was not found"));
        return materialMapper.toDto(foundMaterial);
    }

//    @Override
//    @Transactional
//    public MaterialDto update(Long id, MaterialDto dto) {
//        return materialRepository.findById(id)
//                .map(material -> materialMapper.toEntity(material, dto))
//                .map(materialRepository::save)
//                .map(materialMapper::toDto)
//                .orElseThrow(() -> new RuntimeException("material with id " + id.toString() + " was not found"));
//    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        materialRepository.deleteById(id);
    }

    @Override
    public Page<MaterialDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return materialRepository.findAll(pageable).map(materialMapper::toDto);
    }
}

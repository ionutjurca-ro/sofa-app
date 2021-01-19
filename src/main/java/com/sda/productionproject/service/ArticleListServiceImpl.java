package com.sda.productionproject.service;

import com.sda.productionproject.dto.ArticleListDto;
import com.sda.productionproject.dto.ArticleListMapper;
import com.sda.productionproject.model.*;
import com.sda.productionproject.repository.ArticleListRepository;
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
public class ArticleListServiceImpl implements ArticleListService {
    private ArticleListRepository articleListRepository;
    private ArticleListMapper articleListMapper;

    public ArticleListServiceImpl(ArticleListRepository articleListRepository, ArticleListMapper articleListMapper) {
        this.articleListRepository = articleListRepository;
        this.articleListMapper = articleListMapper;
    }

    @Override
    @Transactional
    public ArticleListDto save(ArticleListDto dto) {
        ArticleList savedArticleList = articleListRepository.save(articleListMapper.toEntity(dto));
        return articleListMapper.toDto(savedArticleList);
    }

    @Override
    public Set<ArticleListDto> findAll() {
        Set<ArticleListDto> articleListDtos = new HashSet<>();
        articleListRepository.findAll().forEach(m -> articleListDtos.add(articleListMapper.toDto(m)));
        return articleListDtos;
    }

    @Override
    public ArticleListDto findById(Long id) {
        ArticleList foundArticleList = articleListRepository.findById(id).orElseThrow(() -> new RuntimeException("articleList with id " + id.toString() + " was not found"));
        return articleListMapper.toDto(foundArticleList);
    }

//    @Override
//    @Transactional
//    public ArticleListDto update(Long id, ArticleListDto dto) {
//        return articleListRepository.findById(id)
//                .map(articleList -> articleListMapper.toEntity(articleList, dto))
//                .map(articleListRepository::save)
//                .map(articleListMapper::toDto)
//                .orElseThrow(() -> new RuntimeException("articleList with id " + id.toString() + " was not found"));
//    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        articleListRepository.deleteById(id);
    }

    @Override
    public ArticleListDto findByWood(String model, String numberOfSeats, Backside backside) {
        ArticleList foundArticleList = articleListRepository.findByWoodQuery(model, numberOfSeats, backside).orElseThrow(() -> new RuntimeException("articleList wood with model " + model + ", number of seats " + numberOfSeats + ", backside " + backside + " was not found"));
        return articleListMapper.toDto(foundArticleList);
    }

    @Override
    public ArticleListDto findByHardware(String model, String numberOfSeats, Relax relax) {
        ArticleList foundArticleList = articleListRepository.findByHardwareQuery(model, numberOfSeats, relax).orElseThrow(() -> new RuntimeException("articleList hardware with model " + model + ", number of seats " + numberOfSeats + ", relax " + relax + " was not found"));
        return articleListMapper.toDto(foundArticleList);
    }

    @Override
    public ArticleListDto findByPacking(String numberOfSeats) {
        ArticleList foundArticleList = articleListRepository.findByPackingQuery(numberOfSeats).orElseThrow(() -> new RuntimeException("articleList packing with number of seats " + numberOfSeats + " was not found"));
        return articleListMapper.toDto(foundArticleList);
    }

    @Override
    public ArticleListDto findByLeg(String model, String numberOfSeats, Leg leg) {
        ArticleList foundArticleList = articleListRepository.findByLegQuery(model, numberOfSeats, leg).orElseThrow(() -> new RuntimeException("articleList leg with model " + model + ", number of seats " + numberOfSeats + ", leg " + leg + " was not found"));
        return articleListMapper.toDto(foundArticleList);
    }

    @Override
    public ArticleListDto findByPolyether(String model, String numberOfSeats, Confort confort) {
        ArticleList foundArticleList = articleListRepository.findByPolyetherQuery(model, numberOfSeats, confort).orElseThrow(() -> new RuntimeException("articleList polyether with model " + model + ", number of seats " + numberOfSeats + ", confort " + confort + " was not found"));
        return articleListMapper.toDto(foundArticleList);
    }

    @Override
    public ArticleListDto findByAuxiliary(String model, String numberOfSeats) {
        ArticleList foundArticleList = articleListRepository.findByAuxiliaryQuery(model, numberOfSeats).orElseThrow(() -> new RuntimeException("articleList auxiliary with model " + model + ", number of seats " + numberOfSeats + " was not found"));
        return articleListMapper.toDto(foundArticleList);
    }

    @Override
    public Page<ArticleListDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return articleListRepository.findAll(pageable).map(articleListMapper::toDto);
    }
}

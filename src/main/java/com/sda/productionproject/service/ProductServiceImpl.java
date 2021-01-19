package com.sda.productionproject.service;

import com.sda.productionproject.dto.ArticleListDto;
import com.sda.productionproject.dto.ProductDto;
import com.sda.productionproject.dto.ProductMapper;
import com.sda.productionproject.model.Product;
import com.sda.productionproject.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private ArticleListService articleListService;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ArticleListService articleListService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.articleListService = articleListService;
    }

    @Override
    @Transactional
    public ProductDto save(ProductDto dto) {
        Product savedProduct = productRepository.save(productMapper.toEntity(dto));
        return productMapper.toDto(savedProduct);
    }

    @Override
    public Page<ProductDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    @Override
    public Set<ProductDto> findAll() {
        Set<ProductDto> productDtos = new HashSet<>();
        productRepository.findAll().forEach(m -> productDtos.add(productMapper.toDto(m)));
        return productDtos;
    }

    @Override
    public ProductDto findById(Long id) {
        Product foundProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product with id " + id.toString() + " was not found"));
        return productMapper.toDto(foundProduct);
    }

    @Override
    @Transactional
    public ProductDto update(Long id, ProductDto dto) {
        return productRepository.findById(id)
                .map(product -> productMapper.toEntity(product, dto))
                .map(productRepository::save)
                .map(productMapper::toDto)
                .orElseThrow(() -> new RuntimeException("product with id " + id.toString() + " was not found"));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void assignMaterials(Set<ProductDto> productDtos) {
        for (ProductDto productDto : productDtos) {
            for (ArticleListDto articleListDto : articleListDtos(productDto)){
                productDto.getMaterials().putAll(articleListDto.getMaterials());
            }
        }
    }

    @Override
    public Set<ProductDto> productsWithoutMaterials() {
        Set<ProductDto> productDtos = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            if (product.getMaterials().isEmpty()) {
                productDtos.add(productMapper.toDto(product));
            }
        }
        return productDtos;
    }

    public Set<ArticleListDto> articleListDtos(ProductDto productDto){
        ArticleListDto articleListDtoByWood = articleListService.findByWood(productDto.getModel(), productDto.getNumberOfSeats(), productDto.getBackside());
        ArticleListDto articleListDtoByHardware = articleListService.findByHardware(productDto.getModel(), productDto.getNumberOfSeats(), productDto.getRelax());
        ArticleListDto articleListDtoByPacking = articleListService.findByPacking(productDto.getNumberOfSeats());
        ArticleListDto articleListDtoByLeg = articleListService.findByLeg(productDto.getModel(), productDto.getNumberOfSeats(), productDto.getLeg());
        ArticleListDto articleListDtoByPolyether = articleListService.findByPolyether(productDto.getModel(), productDto.getNumberOfSeats(), productDto.getConfort());
        ArticleListDto articleListDtoByAuxiliary = articleListService.findByAuxiliary(productDto.getModel(), productDto.getNumberOfSeats());
        return new HashSet<>(Arrays.asList(articleListDtoByWood, articleListDtoByHardware, articleListDtoByPacking, articleListDtoByLeg, articleListDtoByPolyether, articleListDtoByAuxiliary));
    }
}

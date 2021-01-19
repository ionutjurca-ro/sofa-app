package com.sda.productionproject.config;

import com.sda.productionproject.model.*;
import com.sda.productionproject.repository.ArticleListRepository;
import com.sda.productionproject.repository.MaterialRepository;
import com.sda.productionproject.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class InitialDb implements ApplicationListener<ContextRefreshedEvent> {

    private MaterialRepository materialRepository;
    private ProductRepository productRepository;
    private ArticleListRepository articleListRepository;

    public InitialDb(MaterialRepository materialRepository, ProductRepository productRepository, ArticleListRepository articleListRepository) {
        this.materialRepository = materialRepository;
        this.productRepository = productRepository;
        this.articleListRepository = articleListRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createMaterials();
        productRepository.saveAll(createProducts());
        articleListRepository.saveAll(createArticleList());
    }

    private void createMaterials(){
        Material material1 = new Material();
        material1.setName("fag 850x50x15");
        material1.setPrice(2.5);

        Material material2 = new Material();
        material2.setName("fag 250x100x15");
        material2.setPrice(3.7);

        Material material3 = new Material();
        material3.setName("mdf 50x263x12");
        material3.setPrice(6.3);

        Material material4 = new Material();
        material4.setName("piulita cu gheara M8");
        material4.setPrice(6.1);

        Material material5 = new Material();
        material5.setName("surub m8");
        material5.setPrice(0.2);

        List<Material> materials = new ArrayList<>(Arrays.asList(material1, material2, material3, material4, material5));
        materialRepository.saveAll(materials);
    }

    private List<Product> createProducts() {
        Product product1 = new Product();
        product1.setModel("portland");
        product1.setNumberOfSeats("2.5 seats");
        product1.setBackside(Backside.high);
        Material material1 = materialRepository.findByName("fag 850x50x15").orElseThrow(() -> new RuntimeException("material was not found"));
        Material material2 = materialRepository.findByName("fag 250x100x15").orElseThrow(() -> new RuntimeException("material was not found"));
        product1.getMaterials().put(material1, 1.6);
        product1.getMaterials().put(material2, 2.3);

        Product product2 = new Product();
        product2.setModel("desley");
        product2.setNumberOfSeats("3 seats");
        product2.setConfort(Confort.soft);
        product2.setLeg(Leg.BLACK_LEGS);

        return Arrays.asList(product1, product2);
    }

    private List<ArticleList> createArticleList() {
        ArticleList articleList1 = new ArticleList();
        articleList1.setModel("portland");
        articleList1.setNumberOfSeats("2.5 seats");
        articleList1.setBackside(Backside.high);
        articleList1.setMaterialType(MaterialType.WOOD);
        Material material1 = materialRepository.findByName("fag 850x50x15").orElseThrow(() -> new RuntimeException("material was not found"));
        Material material2 = materialRepository.findByName("fag 250x100x15").orElseThrow(() -> new RuntimeException("material was not found"));
        articleList1.getMaterials().put(material1, 2.35);
        articleList1.getMaterials().put(material2, 3.6);

        ArticleList articleList2 = new ArticleList();
        articleList2.setModel("portland");
        articleList2.setNumberOfSeats("2.5 seats");
        articleList2.setMaterialType(MaterialType.HARDWARE);
        Material material5 = materialRepository.findByName("piulita cu gheara M8").orElseThrow(() -> new RuntimeException("material was not found"));
        articleList2.getMaterials().put(material5,5.8);

        ArticleList articleList3 = new ArticleList();
        articleList3.setModel("desley");
        articleList3.setNumberOfSeats("3 seats");
        articleList3.setMaterialType(MaterialType.WOOD);
        Material material3 = materialRepository.findByName("mdf 50x263x12").orElseThrow(() -> new RuntimeException("material was not found"));
        articleList3.getMaterials().put(material3, Double.valueOf(26));

        ArticleList articleList4 = new ArticleList();
        articleList4.setModel("desley");
        articleList4.setNumberOfSeats("3 seats");
        articleList4.setMaterialType(MaterialType.HARDWARE);
        Material material4 = materialRepository.findByName("surub m8").orElseThrow(() -> new RuntimeException("material was not found"));
        articleList4.getMaterials().put(material4, 15.3);

        return Arrays.asList(articleList1, articleList2, articleList3, articleList4);
    }
}

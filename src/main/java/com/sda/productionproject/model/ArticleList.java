package com.sda.productionproject.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity(name = "ArticleList")
@Table(name = "articleList")
@Getter
@Setter
public class ArticleList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_list_id")
    private Long articleListId;

    private String model;

    @Column(name = "number_of_seats")
    private String numberOfSeats;

    @Enumerated
    private Backside backside;

    @Enumerated
    private Confort confort;

    @Enumerated
    private Relax relax;

    @Enumerated
    private Leg leg;

    @Column(name = "material_type")
    @Enumerated
    private MaterialType materialType;

    @ElementCollection
    @CollectionTable(name="articleList_materials", joinColumns=@JoinColumn(name="articleListId"))
    @Column(name="quantity")
    @MapKeyJoinColumn(name="materialId", referencedColumnName="materialId")
    private Map<Material, Double> materials = new HashMap<>();

    public ArticleList() {
    }

}

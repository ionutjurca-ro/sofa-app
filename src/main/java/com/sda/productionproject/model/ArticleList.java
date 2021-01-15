package com.sda.productionproject.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    @Column(name = "seat_depth")
    private SeatDepth seatDepth;

    @Enumerated
    private Relax relax;

    @Enumerated
    private Leg leg;

    @Enumerated
    @Column(name = "leg_height")
    private LegHeight legHeight;

    @Enumerated
    private Arm arm;

    @Enumerated
    private Headrest headrest;

    @Enumerated
    private Upholstered upholstered;

    @Column(name = "material_type")
    @Enumerated
    private MaterialType materialType;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Material> materials = new LinkedHashSet<>();

    @ElementCollection(targetClass = Double.class)
    private List<Double> quantities = new ArrayList<>();

    public ArticleList() {
    }
}

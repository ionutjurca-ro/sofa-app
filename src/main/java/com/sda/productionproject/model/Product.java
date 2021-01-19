package com.sda.productionproject.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity(name = "Product")
@Table(name = "product")
@Getter
@Setter
@EqualsAndHashCode
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long productId;

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

    @ElementCollection
    @CollectionTable(name="product_materials", joinColumns=@JoinColumn(name="productId"))
    @Column(name="quantity")
    @MapKeyJoinColumn(name="materialId", referencedColumnName="materialId")
    private Map<Material, Double> materials = new HashMap<>();

    public Product() {
        super();
    }


}

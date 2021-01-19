package com.sda.productionproject.model;

import com.opencsv.bean.CsvBindByName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Material")
@Table(name = "material")
@Getter
@Setter
@EqualsAndHashCode
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "material_id")
    private Long materialId;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private Double price;

    @ManyToOne
    private Product product;

    public Material() {
    }

}

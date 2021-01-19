package com.sda.productionproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialDto {

    private Long materialId;
    private String name;
    private Double price;

    public MaterialDto() {
    }
}

package com.sda.productionproject.dto;

import com.sda.productionproject.model.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class ArticleListDto {

    private Long articleListId;
    private String model;
    private String numberOfSeats;
    private Backside backside;
    private Confort confort;
    private Relax relax;
    private Leg leg;
    private MaterialType materialType;
    private Map<MaterialDto, Double> materials = new HashMap<>();

    public ArticleListDto() {
    }
}

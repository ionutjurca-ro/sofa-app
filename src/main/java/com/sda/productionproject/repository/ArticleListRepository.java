package com.sda.productionproject.repository;

import com.sda.productionproject.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleListRepository extends JpaRepository<ArticleList, Long> {
    final String modelParam = "(a.model = :model OR a.model IS NULL)";
    final String numberOfSeatsParam = "(a.numberOfSeats = :numberOfSeats OR a.numberOfSeats IS NULL)";
    final String backsideParam = "(a.backside = :backside OR a.backside IS NULL)";
    final String confortParam = "(a.confort = :confort OR a.confort IS NULL)";
    final String relaxParam = "(a.relax = :relax OR a.relax IS NULL)";
    final String legParam = "(a.leg = :leg OR a.leg IS NULL)";

    @Query("FROM ArticleList a WHERE a.materialType = com.sda.productionproject.model.MaterialType.WOOD AND " +
            modelParam + " AND " +
            numberOfSeatsParam + " AND " +
            backsideParam)
    Optional<ArticleList> findByWoodQuery(String model, String numberOfSeats, Backside backside);

    @Query("FROM ArticleList a WHERE a.materialType = com.sda.productionproject.model.MaterialType.HARDWARE AND " +
            modelParam + " AND " +
            numberOfSeatsParam + " AND " +
            relaxParam)
    Optional<ArticleList> findByHardwareQuery(String model, String numberOfSeats, Relax relax);

    @Query("FROM ArticleList a WHERE a.materialType = com.sda.productionproject.model.MaterialType.PACKING AND " +
            numberOfSeatsParam)
    Optional<ArticleList> findByPackingQuery(String numberOfSeats);

    @Query("FROM ArticleList a WHERE a.materialType = com.sda.productionproject.model.MaterialType.LEG AND " +
            modelParam + " AND " +
            numberOfSeatsParam + " AND " +
            legParam)
    Optional<ArticleList> findByLegQuery(String model, String numberOfSeats, Leg leg);

    @Query("FROM ArticleList a WHERE a.materialType = com.sda.productionproject.model.MaterialType.POLYETHER AND " +
            modelParam + " AND " +
            numberOfSeatsParam + " AND " +
            confortParam)
    Optional<ArticleList> findByPolyetherQuery(String model, String numberOfSeats, Confort confort);

    @Query("FROM ArticleList a WHERE a.materialType = com.sda.productionproject.model.MaterialType.AUXILIARY AND " +
            modelParam + " AND " +
            numberOfSeatsParam)
    Optional<ArticleList> findByAuxiliaryQuery(String model, String numberOfSeats);

}

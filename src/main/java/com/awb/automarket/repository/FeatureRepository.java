package com.awb.automarket.repository;

import com.awb.automarket.entity.Feature;
import com.awb.automarket.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {

    @Query("select m from Feature m where lower(m.name) = lower(:name)")
    Optional<Feature> findByName(@Param("name") String name);
}

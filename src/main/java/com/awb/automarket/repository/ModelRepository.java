package com.awb.automarket.repository;

import com.awb.automarket.entity.Brand;
import com.awb.automarket.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {

    @Query("select m from Model m where lower(m.name) = lower(:name)")
    Optional<Model> findByName(@Param("name") String name);
}

package com.awb.automarket.repository;

import com.awb.automarket.entity.BodyStyle;
import com.awb.automarket.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BodyStyleRepository extends JpaRepository<BodyStyle, Integer> {

    @Query("select m from BodyStyle m where lower(m.name) = lower(:name)")
    Optional<BodyStyle> findByName(@Param("name") String name);
}

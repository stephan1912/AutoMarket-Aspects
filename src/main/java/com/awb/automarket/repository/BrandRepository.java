package com.awb.automarket.repository;

import com.awb.automarket.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query("select b from Brand b where lower(b.name) = lower(:name)")
    Optional<Brand> findByName(@Param("name") String name);


    @Query("select b from Brand b where lower(b.name) like lower(:name)")
    Optional<List<Brand>> findByPartialName(@Param("name") String name);
}

package com.awb.automarket.repository;

import com.awb.automarket.entity.Country;
import com.awb.automarket.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query("select c from Country c where lower(c.name) = lower(:name)")
    Optional<Country> findByName(@Param("name") String name);

}

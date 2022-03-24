package com.awb.automarket.repository;

import com.awb.automarket.entity.Advert;
import com.awb.automarket.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, Integer> {

    @Query("select a from Advert a where a.user.id = :user_id")
    List<Advert> findByUser(@Param("user_id") Integer user_id);


    Page<Advert> findAll(Pageable pageable);
}

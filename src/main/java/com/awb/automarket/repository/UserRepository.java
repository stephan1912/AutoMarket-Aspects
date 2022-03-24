package com.awb.automarket.repository;

import com.awb.automarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);


    Optional<User> findByUsername(String username);
}

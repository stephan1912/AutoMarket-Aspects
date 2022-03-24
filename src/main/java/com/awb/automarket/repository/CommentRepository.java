package com.awb.automarket.repository;

import com.awb.automarket.entity.Comment;
import com.awb.automarket.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("select m from Comment m where m.user.id = :userId")
    Optional<List<Comment>> findAllUserComments(@Param("userId") Integer userId);
}

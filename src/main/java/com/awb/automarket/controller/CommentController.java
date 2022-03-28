package com.awb.automarket.controller;

import com.awb.automarket.customvalidation.CustomValidator;
import com.awb.automarket.customvalidation.RequireValidation;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.commentDto.CommentDTO;
import com.awb.automarket.services.ICommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/comment")
@RestController
public class CommentController {
    Logger logger =  LoggerFactory.getLogger(CommentController.class);

    @Autowired
    ICommentService commentService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequireValidation
    public ResponseEntity CreateComment(@RequestBody CommentDTO commentDTO){
        return commentService.save(commentDTO).toResponseEntity(logger);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @RequireValidation
    public ResponseEntity UpdateComment(@RequestBody CommentDTO commentDTO){
        return commentService.update(commentDTO).toResponseEntity(logger);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity DeleteComment(@PathVariable("id")Integer id){
        return commentService.deleteById(id).toResponseEntity(logger);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity GetById(@PathVariable("id")Integer id){
        return commentService.findById(id).toResponseEntity(logger);
    }

    @GetMapping(path = "/all/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity GetAll(@PathVariable("userId")Integer userId){
        return commentService.findAllUserComments(userId).toResponseEntity(logger);
    }
}

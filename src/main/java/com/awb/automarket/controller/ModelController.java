package com.awb.automarket.controller;

import com.awb.automarket.customvalidation.CustomValidator;
import com.awb.automarket.customvalidation.RequireValidation;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.modelDto.CreateModelRequest;
import com.awb.automarket.services.IModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/model")
@RestController
public class ModelController {

    Logger logger =  LoggerFactory.getLogger(ModelController.class);

    @Autowired
    IModelService modelService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequireValidation
    public ResponseEntity CreateModel(@RequestBody CreateModelRequest request){
        return modelService.save(request.getBrand_id(), request.toModel()).toResponseEntity(logger);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequireValidation
    public ResponseEntity UpdateById(@RequestBody CreateModelRequest request){
        return modelService.update(request.toModel()).toResponseEntity(logger);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity DeleteById(@PathVariable("id") Integer id){
        return modelService.deleteById(id).toResponseEntity(logger);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity GetById(@PathVariable("id") Integer id){
        return modelService.findById(id).toResponseEntity(logger);
    }

    @GetMapping(path = "/name/{name}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity GetByName(@PathVariable("name") String name){
        return modelService.findByName(name).toResponseEntity(logger);
    }
}
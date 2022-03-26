package com.awb.automarket.controller;

import com.awb.automarket.customvalidation.CustomValidator;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.bodyStyleDto.BodyStyleDTO;
import com.awb.automarket.services.IBodyStyleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequestMapping("api/v1/bodyStyle")
@RestController
public class BodyStyleController {

    Logger logger =  LoggerFactory.getLogger(BodyStyleController.class);

    @Autowired
    IBodyStyleService bodyStyleService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity CreateBodyStyle(@RequestBody BodyStyleDTO bs){
        ServiceResponseModel validationResult = CustomValidator.ValidateObject(bs);

        if(validationResult != null) return validationResult.toResponseEntity(logger);

        return bodyStyleService.save(bs).toResponseEntity(logger);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity UpdateBodyStyle(@RequestBody BodyStyleDTO bs){
        ServiceResponseModel validationResult = CustomValidator.ValidateObject(bs);

        if(validationResult != null) return validationResult.toResponseEntity(logger);

        return bodyStyleService.update(bs).toResponseEntity(logger);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity DeleteBodyStyle(@PathVariable("id")Integer id){
        return bodyStyleService.deleteById(id).toResponseEntity(logger);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity GetById(@PathVariable("id")Integer id){
        return bodyStyleService.findById(id).toResponseEntity(logger);
    }

    @GetMapping(path = "/all")
    public ResponseEntity GetAll(){
        return bodyStyleService.findAll().toResponseEntity(logger);
    }
}

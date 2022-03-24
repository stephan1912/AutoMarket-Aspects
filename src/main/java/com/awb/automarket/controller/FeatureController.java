package com.awb.automarket.controller;

import com.awb.automarket.customvalidation.CustomValidator;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.entity.Feature;
import com.awb.automarket.services.IFeatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/feature")
@RestController
public class FeatureController {


    Logger logger =  LoggerFactory.getLogger(FeatureController.class);

    @Autowired
    IFeatureService featureService;


    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity CreateFeature(@RequestBody Feature feature){
        ServiceResponseModel validationResult = CustomValidator.ValidateObject(feature);

        if(validationResult != null) return validationResult.toResponseEntity(logger);

        return featureService.save(feature).toResponseEntity(logger);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity UpdateFeature(@RequestBody Feature feature){
        ServiceResponseModel validationResult = CustomValidator.ValidateObject(feature);

        if(validationResult != null) return validationResult.toResponseEntity(logger);

        return featureService.update(feature).toResponseEntity(logger);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity DeleteFeature(@PathVariable("id")Integer id){
        return featureService.deleteById(id).toResponseEntity(logger);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity GetById(@PathVariable("id")Integer id){
        return featureService.findById(id).toResponseEntity(logger);
    }

    @GetMapping(path = "/all")
    public ResponseEntity GetAll(){
        return featureService.findAll().toResponseEntity(logger);
    }
}

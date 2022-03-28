package com.awb.automarket.controller;

import com.awb.automarket.customvalidation.CustomValidator;
import com.awb.automarket.customvalidation.RequireValidation;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.brandDto.BrandDTO;
import com.awb.automarket.dto.modelDto.ModelDTO;
import com.awb.automarket.services.BrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/brand")
@RestController
public class BrandController {

    Logger logger =  LoggerFactory.getLogger(BrandController.class);
    @Autowired
    BrandService brandService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequireValidation
    public ResponseEntity CreateBrand(@RequestBody BrandDTO brand){
        return brandService.save(brand).toResponseEntity(logger);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequireValidation
    public ResponseEntity EditBrand(@RequestBody BrandDTO brand){
        return brandService.update(brand).toResponseEntity(logger);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity DeleteById(@PathVariable("id") Integer id){
        return brandService.deleteById(id).toResponseEntity(logger);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity FindById(@PathVariable("id") Integer id){
        return brandService.findById(id).toResponseEntity(logger);
    }

    @GetMapping(path = "/name/{name}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity FindByName(@PathVariable("name") String name){
        return brandService.findByName(name).toResponseEntity(logger);
    }

    @GetMapping(path = "/partial/{name}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity FindByPartialName(@PathVariable("name") String name){
        return brandService.findByPartialName(name).toResponseEntity(logger);
    }

    @GetMapping(path = "/all")
    public ResponseEntity GetAll(){
        return brandService.findAll().toResponseEntity(logger);
    }


    @GetMapping(path = "/{id}/models")
    public ResponseEntity GetAllModels(@PathVariable("id") Integer id){
        return brandService.getModels(id).toResponseEntity(logger);
    }

    @DeleteMapping(path = "/{brandId}/model/{modelId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity GetAllModels(@PathVariable("brandId") Integer brandId, @PathVariable("modelId") Integer modelId){
        return brandService.removeModel(brandId, modelId).toResponseEntity(logger);
    }

    @PostMapping(path = "/{brandId}/model")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity AddModel(@PathVariable("brandId") Integer brandId, @RequestBody ModelDTO model){
        ServiceResponseModel validationResult = CustomValidator.ValidateObject(model);

        if(validationResult != null) return validationResult.toResponseEntity(logger);
        return brandService.addModel(brandId, model).toResponseEntity(logger);
    }
}

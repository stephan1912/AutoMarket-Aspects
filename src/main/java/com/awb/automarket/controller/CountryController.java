package com.awb.automarket.controller;

import com.awb.automarket.customvalidation.CustomValidator;
import com.awb.automarket.customvalidation.RequireValidation;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.countryDto.CountryDTO;
import com.awb.automarket.services.ICountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/country")
@RestController
public class CountryController {

    Logger logger =  LoggerFactory.getLogger(CountryController.class);
    @Autowired
    ICountryService countryService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequireValidation
    public ResponseEntity CreateCountry(@RequestBody CountryDTO country){
        return  countryService.save(country).toResponseEntity(logger);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequireValidation
    public ResponseEntity UpdateCountry(@RequestBody CountryDTO country){
        return  countryService.update(country).toResponseEntity(logger);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity DeleteById(@PathVariable("id")Integer id){
        return countryService.deleteById(id).toResponseEntity(logger);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity GetById(@PathVariable("id")Integer id){
        return countryService.findById(id).toResponseEntity(logger);
    }

    @GetMapping(path = "/all")
    public ResponseEntity GetAll(){
        return countryService.findAll().toResponseEntity(logger);
    }

    @GetMapping(path = "/name/{name}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity GetByName(@PathVariable("name")String name){
        return countryService.findByName(name).toResponseEntity(logger);
    }
}

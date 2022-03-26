package com.awb.automarket.services;

import com.awb.automarket.dto.countryDto.CountryDTO;
import com.awb.automarket.dto.ServiceResponseModel;

public interface ICountryService {
    ServiceResponseModel findAll();
    ServiceResponseModel findById(Integer id);
    ServiceResponseModel findByName(String name);
    ServiceResponseModel save(CountryDTO country);
    ServiceResponseModel update(CountryDTO country);
    ServiceResponseModel deleteById(Integer id);
}

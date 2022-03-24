package com.awb.automarket.services;

import com.awb.automarket.dto.brandDto.BrandDTO;
import com.awb.automarket.dto.modelDto.ModelDTO;
import com.awb.automarket.dto.ServiceResponseModel;

public interface IBrandService {

    ServiceResponseModel findAll();
    ServiceResponseModel findById(Integer id);
    ServiceResponseModel save(BrandDTO brand);
    ServiceResponseModel deleteById(Integer id);

    ServiceResponseModel getModels(BrandDTO brand);
    ServiceResponseModel getModels(Integer id);

    ServiceResponseModel addModel(Integer brandId, ModelDTO model);
    ServiceResponseModel removeModel(Integer brandId, Integer modelId);

    ServiceResponseModel findByName(String name);
    ServiceResponseModel findByPartialName(String name);

    ServiceResponseModel update(BrandDTO brand);
}

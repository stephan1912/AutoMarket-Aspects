package com.awb.automarket.services;

import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.entity.Model;

public interface IModelService {
    ServiceResponseModel findById(Integer id);
    ServiceResponseModel findByName(String name);
    ServiceResponseModel update(Model model);
    ServiceResponseModel save(Integer brandId, Model model);
    ServiceResponseModel deleteById(Integer id);
}

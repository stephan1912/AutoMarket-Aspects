package com.awb.automarket.services;

import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.bodyStyleDto.BodyStyleDTO;
import com.awb.automarket.entity.Feature;

public interface IFeatureService {
    ServiceResponseModel findById(Integer id);
    ServiceResponseModel findAll();
    ServiceResponseModel deleteById(Integer id);
    ServiceResponseModel save(Feature feature);
    ServiceResponseModel update(Feature feature);
}

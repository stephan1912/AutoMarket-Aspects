package com.awb.automarket.services;

import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.bodyStyleDto.BodyStyleDTO;

public interface IBodyStyleService {

    ServiceResponseModel findById(Integer id);
    ServiceResponseModel findAll();
    ServiceResponseModel deleteById(Integer id);
    ServiceResponseModel save(BodyStyleDTO bs);
    ServiceResponseModel update(BodyStyleDTO bs);

}

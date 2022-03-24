package com.awb.automarket.services;

import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.advertDto.AdvertFilter;
import com.awb.automarket.dto.advertDto.CreateAdvertRequest;
import com.awb.automarket.filter.SearchCriteria;

import java.util.List;

public interface IAdvertService {
    ServiceResponseModel findById(Integer id);
    ServiceResponseModel findAll();
    ServiceResponseModel filterResults(List<SearchCriteria> params, AdvertFilter advertFilter, int pageNumber);
    ServiceResponseModel findAllUserAdverts(Integer userId);
    ServiceResponseModel save(CreateAdvertRequest advertRequest, List<String> filesUploaded);
    ServiceResponseModel update(CreateAdvertRequest advertRequest);
    ServiceResponseModel deleteById(Integer id);
    ServiceResponseModel findAll(int page, String sort);
}

package com.awb.automarket.services;

import com.awb.automarket.dto.*;
import com.awb.automarket.dto.brandDto.BrandDTO;
import com.awb.automarket.dto.brandDto.BrandResponse;
import com.awb.automarket.dto.modelDto.ModelDTO;
import com.awb.automarket.dto.modelDto.ModelResponse;
import com.awb.automarket.entity.BodyStyle;
import com.awb.automarket.entity.Brand;
import com.awb.automarket.entity.Model;
import com.awb.automarket.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandService implements IBrandService{

    BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public ServiceResponseModel findAll() {
         return ServiceResponseModel.ok(brandRepository.findAll().stream().map(brand -> new BrandResponse(brand)).collect(Collectors.toList()));
    }

    @Override
    public ServiceResponseModel findById(Integer id) {
        Optional<Brand> brand = brandRepository.findById(id);
        if(brand.isPresent()) return ServiceResponseModel.ok(new BrandResponse(brand.get()));
        return ServiceResponseModel.ClassNotFound(Brand.class);
    }

    @Override
    public ServiceResponseModel save(BrandDTO brand) {

        ServiceResponseModel checkExisting = findByName(brand.getName());
        if(checkExisting.getErrorResponse() == null) return ServiceResponseModel.Conflict("Exista deja un brand cu acest nume!");

        Brand saved = brandRepository.save(brand.toBrand());
        return ServiceResponseModel.ok(new BrandResponse(saved));
    }

    @Override
    public ServiceResponseModel deleteById(Integer id) {
        Optional<Brand> exists = brandRepository.findById(id);
        if(exists.isEmpty())
            return ServiceResponseModel.ClassNotFound(Brand.class);
        Brand brand = exists.get();

        List<Model> models = new ArrayList<Model>();
        brand.getModelList().iterator().forEachRemaining(models::add);

        for(Model model: models){
            brand.removeModel(model);
        }
        brandRepository.save(brand);
        brandRepository.deleteById(id);

        return ServiceResponseModel.ok(null);
    }

    @Override
    public ServiceResponseModel getModels(BrandDTO brand) {
        return getModels(brand.getBrand_id());
    }

    @Override
    public ServiceResponseModel getModels(Integer id) {
        Brand brand = brandRepository.getOne(id);
        if(brand == null)
            return ServiceResponseModel.ClassNotFound(Brand.class);

        return ServiceResponseModel.ok(brand.getModelList().stream().map(model -> new ModelResponse(model)).collect(Collectors.toList()));
    }

    @Override
    public ServiceResponseModel addModel(Integer brandId, ModelDTO model) {
        Optional<Brand> exists = brandRepository.findById(brandId);
        if(exists.isEmpty())
            return ServiceResponseModel.ClassNotFound(Brand.class);
        Brand brandToUpdate = exists.get();

        brandToUpdate.addModel(model.toModel());
        brandRepository.save(brandToUpdate);
        return ServiceResponseModel.ok(new BrandResponse(brandToUpdate));
    }

    @Override
    public ServiceResponseModel removeModel(Integer brandId, Integer modelId) {
        Optional<Brand> exists = brandRepository.findById(brandId);
        if(exists.isEmpty())
            return ServiceResponseModel.ClassNotFound(Brand.class);
        Brand brandToUpdate = exists.get();

        brandToUpdate.removeModel(modelId);
        brandRepository.save(brandToUpdate);
        return ServiceResponseModel.ok(new BrandResponse(brandToUpdate));
    }

    @Override
    public ServiceResponseModel findByName(String name) {
        Optional<Brand> brand = brandRepository.findByName(name);
        if(brand.isPresent()) return ServiceResponseModel.ok(new BrandResponse(brand.get()));
        return ServiceResponseModel.bad(ErrorResponse.NotFound());
    }

    @Override
    public ServiceResponseModel findByPartialName(String name) {
        Optional<List<Brand>> brand = brandRepository.findByPartialName(name);
        if(brand.isPresent()) return ServiceResponseModel.ok(brand.get().stream().map(brand1 -> new BrandResponse(brand1)).collect(Collectors.toList()));
        return ServiceResponseModel.ClassNotFound(Brand.class);
    }

    @Override
    public ServiceResponseModel update(BrandDTO brand) {
        Optional<Brand> exists = brandRepository.findById(brand.brand_id);
        if(exists.isEmpty())
            return ServiceResponseModel.ClassNotFound(Brand.class);
        Brand brandToUpdate = exists.get();


        Optional<Brand> checkExisting = brandRepository.findByName(brand.getName());
        if(checkExisting.isPresent() && checkExisting.get().getId() != brand.getBrand_id()) return ServiceResponseModel.Conflict("Exista deja un brand cu acest nume!");

        brandToUpdate.setCode(brand.getCode());
        brandToUpdate.setName(brand.getName());

        Brand saved = brandRepository.save(brandToUpdate);

        return ServiceResponseModel.ok(new BrandResponse(saved));
    }
}

package com.awb.automarket.services;

import com.awb.automarket.dto.ErrorResponse;
import com.awb.automarket.dto.modelDto.ModelDTO;
import com.awb.automarket.dto.modelDto.ModelResponse;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.entity.Feature;
import com.awb.automarket.entity.Model;
import com.awb.automarket.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModelService implements IModelService{

    ModelRepository modelRepository;
    IBrandService brandService;

    @Autowired
    public ModelService(ModelRepository modelRepository, IBrandService brandService) {
        this.modelRepository = modelRepository;
        this.brandService = brandService;
    }

    @Override
    public ServiceResponseModel findById(Integer id) {
        Optional<Model> model = modelRepository.findById(id);
        if(model.isPresent()) return ServiceResponseModel.ok(new ModelResponse(model.get()));
        return ServiceResponseModel.ClassNotFound(Feature.class);
    }

    @Override
    public ServiceResponseModel findByName(String name) {
        Optional<Model> model = modelRepository.findByName(name);
        if(model.isPresent()) return ServiceResponseModel.ok(new ModelResponse(model.get()));
        return ServiceResponseModel.ClassNotFound(Feature.class);
    }

    @Override
    public ServiceResponseModel update(Model model) {
        Model modelToUpdate = modelRepository.getOne(model.getId());
        if(modelToUpdate == null)
            return ServiceResponseModel.ClassNotFound(Feature.class);

        modelToUpdate.setGeneration(model.getGeneration());
        modelToUpdate.setName(model.getName());
        modelToUpdate.setFinalYear(model.getFinalYear());
        modelToUpdate.setLaunchYear(model.getLaunchYear());

        Model saved = modelRepository.save(modelToUpdate);

        return ServiceResponseModel.ok(new ModelResponse(saved));
    }

    @Override
    public ServiceResponseModel save(Integer brandId, Model model) {
        return brandService.addModel(brandId, new ModelDTO(model));
    }

    @Override
    public ServiceResponseModel deleteById(Integer id) {
        Model model = modelRepository.getOne(id);
        if(model == null)
            return ServiceResponseModel.ClassNotFound(Feature.class);


        return brandService.removeModel(model.getBrand().getId(), model.getId());
    }
}

package com.awb.automarket.services;

import com.awb.automarket.dto.ErrorResponse;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.entity.Country;
import com.awb.automarket.entity.Feature;
import com.awb.automarket.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeatureService implements IFeatureService{
    
    FeatureRepository featureRepository;

    @Autowired
    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Override
    public ServiceResponseModel findById(Integer id) {
        Optional<Feature> feature = featureRepository.findById(id);
        if(feature.isPresent()) return  ServiceResponseModel.ok(feature.get());
        return ServiceResponseModel.ClassNotFound(Feature.class);
    }

    @Override
    public ServiceResponseModel findAll() {
        return ServiceResponseModel.ok(featureRepository.findAll());
    }

    @Override
    public ServiceResponseModel deleteById(Integer id) {
        Optional<Feature> feature = featureRepository.findById(id);
        if(!feature.isPresent())
            return ServiceResponseModel.ClassNotFound(Feature.class);

        Feature toUpdate = feature.get();
        toUpdate.getAdvertList().iterator().forEachRemaining(advert -> advert.removeFeature(toUpdate));

        featureRepository.save(toUpdate);
        featureRepository.deleteById(toUpdate.getId());
        return ServiceResponseModel.ok(null);
    }

    @Override
    public ServiceResponseModel save(Feature feature) {

        Optional<Feature> checkName = featureRepository.findByName(feature.getName());
        if(checkName.isPresent())
            return ServiceResponseModel.Conflict("O optiune cu acest nume exista deja!");

        return ServiceResponseModel.ok(featureRepository.save(feature));
    }

    @Override
    public ServiceResponseModel update(Feature feature) {
        Optional<Feature> isPresent = featureRepository.findById(feature.getId());
        if(isPresent.isEmpty())
            return ServiceResponseModel.ClassNotFound(Feature.class);
        Feature toUpdate = isPresent.get();
        Optional<Feature> checkName = featureRepository.findByName(feature.getName());
        if(checkName.isPresent() && checkName.get().getId() != feature.getId())
            return ServiceResponseModel.Conflict("O optiune cu acest nume exista deja!");

        toUpdate.setName((feature.getName()));

        return ServiceResponseModel.ok(featureRepository.save(toUpdate));
    }
}

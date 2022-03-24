package com.awb.automarket.services;

import com.awb.automarket.customvalidation.CustomValidator;
import com.awb.automarket.dto.ErrorResponse;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.bodyStyleDto.BodyStyleDTO;
import com.awb.automarket.entity.BodyStyle;
import com.awb.automarket.entity.Model;
import com.awb.automarket.repository.BodyStyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BodyStyleService implements IBodyStyleService{

    BodyStyleRepository bodyStyleRepository;

    @Autowired
    public BodyStyleService(BodyStyleRepository bodyStyleRepository) {
        this.bodyStyleRepository = bodyStyleRepository;
    }

    @Override
    public ServiceResponseModel findById(Integer id) {
        Optional<BodyStyle> bs = bodyStyleRepository.findById(id);
        if(bs.isPresent()) return  ServiceResponseModel.ok(new BodyStyleDTO(bs.get()));
        return ServiceResponseModel.ClassNotFound(BodyStyle.class);
    }

    @Override
    public ServiceResponseModel findAll() {
        return ServiceResponseModel.ok(bodyStyleRepository.findAll().stream().map(bodyStyle -> new BodyStyleDTO(bodyStyle)).collect(Collectors.toList()));
    }

    @Override
    public ServiceResponseModel deleteById(Integer id) {
        Optional<BodyStyle> bs = bodyStyleRepository.findById(id);
        if(!bs.isPresent())
            return ServiceResponseModel.ClassNotFound(BodyStyle.class);

        BodyStyle body = bs.get();
        body.getAdvertList().iterator().forEachRemaining(advert -> advert.setBodyStyle(null));

        bodyStyleRepository.save(body);
        bodyStyleRepository.deleteById(body.getId());
        return ServiceResponseModel.ok(null);
    }

    @Override
    public ServiceResponseModel save(BodyStyleDTO bs) {

        Optional<BodyStyle> checkName = bodyStyleRepository.findByName(bs.getName());
        if(checkName.isPresent()) return ServiceResponseModel.bad(new BodyStyleDTO(checkName.get()), ErrorResponse.DuplicateError("O caroserie cu acest nume exista deja!"));


        ServiceResponseModel validationResult = CustomValidator.ValidateObject(bs);

        if(validationResult != null) return validationResult;

        return ServiceResponseModel.ok(new BodyStyleDTO(bodyStyleRepository.save(bs.toBodyStyle())));
    }

    @Override
    public ServiceResponseModel update(BodyStyleDTO bs) {

        Optional<BodyStyle> checkName = bodyStyleRepository.findByName(bs.getName());
        if(checkName.isPresent() && checkName.get().getId() != bs.getBs_id()) return ServiceResponseModel.Conflict("O caroserie cu acest nume exista deja!");

        BodyStyle bStyle = bodyStyleRepository.getOne(bs.getBs_id());
        if(bStyle == null)
            return ServiceResponseModel.ClassNotFound(BodyStyle.class);

        bStyle.setDescription(bs.getDescription());
        bStyle.setName((bs.getName()));

        bodyStyleRepository.save(bStyle);

        return ServiceResponseModel.ok(new BodyStyleDTO(bStyle));
    }
}

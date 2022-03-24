package com.awb.automarket.services;

import com.awb.automarket.dto.ErrorResponse;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.advertDto.AdvertDTO;
import com.awb.automarket.dto.advertDto.AdvertFilter;
import com.awb.automarket.dto.advertDto.AdvertListResponse;
import com.awb.automarket.dto.advertDto.CreateAdvertRequest;
import com.awb.automarket.dto.modelDto.ModelResponse;
import com.awb.automarket.entity.*;
import com.awb.automarket.filter.AdvertSearchQueryCriteriaConsumer;
import com.awb.automarket.filter.SearchCriteria;
import com.awb.automarket.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdvertService implements IAdvertService{

    AdvertRepository advertRepository;
    UserRepository userRepository;
    CountryRepository countryRepository;
    BodyStyleRepository bodyStyleRepository;
    FeatureRepository featureRepository;
    ModelRepository modelRepository;
    ImagesRepository imagesRepository;

    @Autowired
    public AdvertService(AdvertRepository advertRepository, UserRepository userRepository, CountryRepository countryRepository,
                         BodyStyleRepository bodyStyleRepository, FeatureRepository featureRepository,
                         ModelRepository modelRepository, ImagesRepository imagesRepository) {
        this.advertRepository = advertRepository;
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.bodyStyleRepository = bodyStyleRepository;
        this.featureRepository = featureRepository;
        this.modelRepository = modelRepository;
        this.imagesRepository = imagesRepository;
    }

    @Override
    public ServiceResponseModel findById(Integer id) {
        Optional<Advert> advert = advertRepository.findById(id);
        if(advert.isPresent()) return ServiceResponseModel.ok(advert.get().toDTO());
        return ServiceResponseModel.ClassNotFound(Advert.class);
    }

    @Override
    public ServiceResponseModel findAll() {
        return ServiceResponseModel.ok(advertRepository.findAll().stream().map(advert -> advert.toDTO()).collect(Collectors.toList()));
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ServiceResponseModel filterResults(List<SearchCriteria> params, AdvertFilter advertFilter, int pageNumber) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Advert> query = builder.createQuery(Advert.class);
        Root r = query.from(Advert.class);

        Predicate predicate = builder.conjunction();

        AdvertSearchQueryCriteriaConsumer searchConsumer =
                new AdvertSearchQueryCriteriaConsumer(predicate, builder, r);
        params.stream().forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        List<Advert> result = entityManager.createQuery(query).getResultList();
        if(advertFilter.getModel() != -1){
            result = result.stream().filter(advert -> advert.getModel().getId() == advertFilter.getModel()).collect(Collectors.toList());
        }
        else if(advertFilter.getBrand() != -1){
            result = result.stream().filter(advert -> advert.getModel().getBrand().getId() == advertFilter.getBrand()).collect(Collectors.toList());
        }
        if(advertFilter.getBs() != -1){
            result = result.stream().filter(advert -> advert.getBodyStyle().getId() == advertFilter.getBs()).collect(Collectors.toList());
        }

        int totalCount = result.size();
        int pageSize = result.size() >= 1 ? 1 : result.size();

        result = result.subList((pageNumber - 1) * pageSize, pageSize * pageNumber);

        AdvertListResponse response = new AdvertListResponse(
                totalCount,
                result.stream().map(advert -> advert.toDTO()).collect(Collectors.toList())
        );
        return ServiceResponseModel.ok(response);
    }

    @Override
    public ServiceResponseModel findAllUserAdverts(Integer userId) {
        return ServiceResponseModel.ok(advertRepository.findByUser(userId).stream().map(advert -> {
            AdvertDTO dto = advert.toDTO();
            dto.setModel(new ModelResponse(modelRepository.findById(dto.getModel().getModel_id()).get()));
            return dto;
        }).collect(Collectors.toList()));
    }

    @Override
    public ServiceResponseModel save(CreateAdvertRequest advertRequest, List<String> filesUploaded) {
        ServiceResponseModel srm = GenerateAdvertFromRequest(advertRequest, false);
        if(srm.getErrorResponse() != null) return srm;

        Advert toSave = (Advert)srm.getResponseData();
        toSave.setImages(filesUploaded.stream().map(file -> {
            Images img = new Images();
            img.setName(file);
            img.setAdvert(toSave);
            return img;
        }).collect(Collectors.toList()));
        return ServiceResponseModel.ok(advertRepository.save(toSave).toDTO());
    }

    private ServiceResponseModel GenerateAdvertFromRequest(CreateAdvertRequest advertRequest, boolean update) {
        User user = userRepository.getOne(advertRequest.getUser_id());

        if(user == null){
            return ServiceResponseModel.Unauthorized();
        }

        Advert advert = update ? advertRepository.getOne(advertRequest.getAdvert_id()) : new Advert();

        if(advert == null)
            return ServiceResponseModel.ClassNotFound(Advert.class);

        advert.setTitle(advertRequest.getTitle());
        advert.setDescription(advertRequest.getDescription());
        advert.setVin(advertRequest.getVin());
        if(!update) advert.setCreatedAt(new Date());
        advert.setYear(advertRequest.getYear());
        advert.setKm(advertRequest.getKm());
        advert.setPrice(advertRequest.getPrice());
        advert.setEngineCap(advertRequest.getEngineCap());
        advert.setServiceDocs(advertRequest.isServiceDocs());
        advert.setRegistered(advertRequest.isRegistered());
        advert.setHorsePower(advertRequest.getHorsePower());
        advert.setDrivetrain(advertRequest.getDrivetrain());
        advert.setFuel(advertRequest.getFuel());
        advert.setGearboxType(advertRequest.getGearboxType());

        if(!update) advert.setUser(user);
        if((advertRequest.getModel_id() != -1 && update) || !update){
            Model model = modelRepository.getOne(advertRequest.getModel_id());
            if(model == null){
                return ServiceResponseModel.ClassNotFound(Model.class);
            }
            advert.setModel(model);
        }
        if((advertRequest.getCountry_id() != -1 && update) || !update) {
            Country country = countryRepository.getOne(advertRequest.getCountry_id());
            if(country == null){
                return ServiceResponseModel.ClassNotFound(Country.class);
            }
            advert.setCountry(country);
        }
        if((advertRequest.getBodyStyle_id() != -1 && update) || !update){
            BodyStyle bodyStyle = bodyStyleRepository.getOne(advertRequest.getBodyStyle_id());
            if(bodyStyle == null){
                return ServiceResponseModel.ClassNotFound(BodyStyle.class);
            }
            advert.setBodyStyle(bodyStyle);
        }
        if((advertRequest.getFeatures().size() != 0 && update) || !update){
            List<Feature> featureList = advertRequest.getFeatures().stream().map(id -> featureRepository.getOne(id)).collect(Collectors.toList());
            if(featureList.size() != advertRequest.getFeatures().size()){
                return ServiceResponseModel.ClassNotFound(Feature.class);
            }
            advert.setFeatures(featureList);
        }



        return ServiceResponseModel.ok(advert);
    }

    @Override
    public ServiceResponseModel update(CreateAdvertRequest advertRequest) {
        ServiceResponseModel srm = GenerateAdvertFromRequest(advertRequest, true);
        if(srm.getErrorResponse() != null) return srm;

        return ServiceResponseModel.ok(advertRepository.save((Advert)srm.getResponseData()).toDTO());
    }

    @Override
    public ServiceResponseModel deleteById(Integer id) {
        Optional<Advert> advertO = advertRepository.findById(id);

        if(advertO.isEmpty())
            return ServiceResponseModel.ClassNotFound(Advert.class);

        Advert advert = advertO.get();
        advert.setModel(null);
        advert.setCountry(null);
        advert.setFeatures(null);
        advert.setUser(null);


        advertRepository.deleteById(id);
        return ServiceResponseModel.ok(null);
    }

    @Override
    public ServiceResponseModel findAll(int page, String sort) {
        Pageable req = PageRequest.of(page - 1, 1, Sort.by("createdAt").ascending());
        if(sort.toLowerCase(Locale.ROOT).equals("desc")){
            req = PageRequest.of(page - 1, 1, Sort.by("createdAt").descending());
        }

        Page<Advert> result = advertRepository.findAll(req);

        AdvertListResponse listResponse = new AdvertListResponse();
        listResponse.adverts = result.stream().map(advert -> advert.toDTO()).collect(Collectors.toList());
        listResponse.totalCount = (int)result.getTotalElements();

        return ServiceResponseModel.ok(listResponse);
    }
}

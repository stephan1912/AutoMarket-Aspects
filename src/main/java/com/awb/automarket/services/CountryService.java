package com.awb.automarket.services;

import com.awb.automarket.dto.countryDto.CountryDTO;
import com.awb.automarket.dto.ErrorResponse;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.entity.Comment;
import com.awb.automarket.entity.Country;
import com.awb.automarket.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService implements ICountryService{

    CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public ServiceResponseModel findAll() {
        return ServiceResponseModel.ok(countryRepository.findAll().stream().map(country -> new CountryDTO(country)).collect(Collectors.toList()));
    }

    @Override
    public ServiceResponseModel findById(Integer id) {
        Optional<Country> country = countryRepository.findById(id);
        if (country.isPresent()) return ServiceResponseModel.ok(new CountryDTO(country.get()));
        return ServiceResponseModel.ClassNotFound(Country.class);
    }

    @Override
    public ServiceResponseModel findByName(String name) {
        Optional<Country> country = countryRepository.findByName(name);
        if (country.isPresent()) return ServiceResponseModel.ok(new CountryDTO(country.get()));
        return ServiceResponseModel.ClassNotFound(Country.class);
    }

    @Override
    public ServiceResponseModel save(CountryDTO country) {
        ServiceResponseModel srm = findByName(country.getName());
        if(srm.getErrorResponse() == null)
            return ServiceResponseModel.Conflict("O tara cu acest nume exista deja");

        Country saved = countryRepository.save(country.toCountry());
        return ServiceResponseModel.ok(new CountryDTO(saved));
    }

    @Override
    public ServiceResponseModel update(CountryDTO country) {

        Optional<Country> check = countryRepository.findById(country.country_id);
        if (check.isEmpty())
            return ServiceResponseModel.ClassNotFound(Country.class);

        Country toUpdate = check.get();

        Optional<Country> checkName = countryRepository.findByName(country.getName());
        if(checkName.isPresent() && checkName.get().getId() != country.getCountry_id())
            return ServiceResponseModel.Conflict("O tara cu acest nume exista deja");

        toUpdate.setName(country.getName());

        countryRepository.save(toUpdate);

        return ServiceResponseModel.ok(new CountryDTO(toUpdate));
    }

    @Override
    public ServiceResponseModel deleteById(Integer id) {
        Optional<Country> check = countryRepository.findById(id);
        if (check.isEmpty())
            return ServiceResponseModel.ClassNotFound(Country.class);

        Country toDelete = check.get();

        toDelete.getAdvertList().iterator().forEachRemaining(advert -> advert.setCountry(null));

        countryRepository.deleteById(toDelete.getId());
        return ServiceResponseModel.ok(null);
    }
}

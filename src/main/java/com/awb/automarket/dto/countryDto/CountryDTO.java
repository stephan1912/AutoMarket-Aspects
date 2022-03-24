package com.awb.automarket.dto.countryDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.Brand;
import com.awb.automarket.entity.Country;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@CustomDtoClassAnnotation(mappsEntity = Country.class)
public class CountryDTO {

    public int country_id;
    @CustomDtoAnnotation(mappsField = "name")
    public String name;

    public CountryDTO(Country country){
        this.country_id = country.getId();
        this.name = country.getName();
    }

    public Country toCountry(){
        return new Country(country_id ,name, null);
    }


    @SneakyThrows
    @Override
    public String toString(){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }
}

package com.awb.automarket.dto.countryDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.Brand;
import com.awb.automarket.entity.Country;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.*;

@Data
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

	public int getCountry_id() {
		return country_id;
	}

	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CountryDTO [country_id=" + country_id + ", name=" + name + ", toCountry()=" + toCountry()
				+ ", getCountry_id()=" + getCountry_id() + ", getName()=" + getName() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public CountryDTO(int country_id, String name) {
		super();
		this.country_id = country_id;
		this.name = name;
	}

	public CountryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}

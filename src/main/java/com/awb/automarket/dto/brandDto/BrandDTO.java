package com.awb.automarket.dto.brandDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.BodyStyle;
import com.awb.automarket.entity.Brand;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.*;

@Data
@CustomDtoClassAnnotation(mappsEntity = Brand.class)
public class BrandDTO {
    public int brand_id;
    @CustomDtoAnnotation(mappsField = "name")
    public String name;
    @CustomDtoAnnotation(mappsField = "code")
    public String code;

    public Brand toBrand(){
        Brand b = new Brand(brand_id, name, code, null);
        return b;
    }

	public int getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(int brand_id) {
		this.brand_id = brand_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "BrandDTO [brand_id=" + brand_id + ", name=" + name + ", code=" + code + ", toBrand()=" + toBrand()
				+ ", getBrand_id()=" + getBrand_id() + ", getName()=" + getName() + ", getCode()=" + getCode()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public BrandDTO(int brand_id, String name, String code) {
		super();
		this.brand_id = brand_id;
		this.name = name;
		this.code = code;
	}

	public BrandDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    
}

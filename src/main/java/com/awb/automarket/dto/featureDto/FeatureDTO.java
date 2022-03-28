package com.awb.automarket.dto.featureDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.Brand;
import com.awb.automarket.entity.Feature;

@CustomDtoClassAnnotation(mappsEntity = Feature.class)
public class FeatureDTO {
    public int id;
    @CustomDtoAnnotation(mappsField = "name")
    public String name;

    public FeatureDTO(Feature feature){
        this.id = feature.getId();
        this.name = feature.getName();
    }

	public FeatureDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

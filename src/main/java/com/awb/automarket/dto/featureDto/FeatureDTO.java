package com.awb.automarket.dto.featureDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.Brand;
import com.awb.automarket.entity.Feature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@CustomDtoClassAnnotation(mappsEntity = Feature.class)
public class FeatureDTO {
    public int id;
    @CustomDtoAnnotation(mappsField = "name")
    public String name;

    public FeatureDTO(Feature feature){
        this.id = feature.getId();
        this.name = feature.getName();
    }
}

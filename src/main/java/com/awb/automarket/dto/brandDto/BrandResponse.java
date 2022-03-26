package com.awb.automarket.dto.brandDto;

import com.awb.automarket.dto.modelDto.ModelDTO;
import com.awb.automarket.entity.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponse {
    public int brand_id;
    public String name;
    public String code;
    public List<ModelDTO> models;
    
    public BrandResponse(Brand brand){
        this.brand_id = brand.getId();
        this.name = brand.getName();
        this.code = brand.getCode();
        if(brand.getModelList() != null) {
            this.models = brand.getModelList().stream().map(model -> new ModelDTO(
                    model.getId(),
                    model.getName(),
                    model.getGeneration(),
                    model.getLaunchYear(),
                    model.getFinalYear()
            )).collect(Collectors.toList());
        }
        else{
            this.models = new ArrayList<>();
        }
    }
}

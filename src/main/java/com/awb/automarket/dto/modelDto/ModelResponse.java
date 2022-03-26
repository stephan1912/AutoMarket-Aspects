package com.awb.automarket.dto.modelDto;

import com.awb.automarket.dto.brandDto.BrandDTO;
import com.awb.automarket.entity.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ModelResponse {
    public int model_id;
    public String name;
    public String generation;
    public int launchYear;
    public int finalYear;
    public BrandDTO brand;
    public String brandName;

    public ModelResponse(Model model){
        this.model_id = model.getId();
        this.name = model.getName();
        this.generation = model.getGeneration();
        this.launchYear = model.getLaunchYear();
        this.finalYear = model.getFinalYear();
        if(model.getBrand() == null) return;;
        brandName = model.getBrand().getName();
        this.brand = new BrandDTO(
                model.getBrand().getId(),
                model.getBrand().getName(),
                model.getBrand().getCode()
        );
    }
}

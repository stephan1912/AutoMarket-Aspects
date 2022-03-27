package com.awb.automarket.dto.modelDto;

import com.awb.automarket.dto.brandDto.BrandDTO;
import com.awb.automarket.entity.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
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

	public int getModel_id() {
		return model_id;
	}

	public void setModel_id(int model_id) {
		this.model_id = model_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGeneration() {
		return generation;
	}

	public void setGeneration(String generation) {
		this.generation = generation;
	}

	public int getLaunchYear() {
		return launchYear;
	}

	public void setLaunchYear(int launchYear) {
		this.launchYear = launchYear;
	}

	public int getFinalYear() {
		return finalYear;
	}

	public void setFinalYear(int finalYear) {
		this.finalYear = finalYear;
	}

	public BrandDTO getBrand() {
		return brand;
	}

	public void setBrand(BrandDTO brand) {
		this.brand = brand;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
    
}

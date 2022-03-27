package com.awb.automarket.dto.modelDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.Brand;
import com.awb.automarket.entity.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@CustomDtoClassAnnotation(mappsEntity = Model.class)
public class CreateModelRequest {
    public int brand_id;
    public int model_id;
    @CustomDtoAnnotation(mappsField = "name")
    public String name;
    @CustomDtoAnnotation(mappsField = "generation")
    public String generation;
    @CustomDtoAnnotation(mappsField = "launchYear")
    public int launchYear;
    @CustomDtoAnnotation(mappsField = "finalYear")
    public int finalYear;

    public Model toModel(){
        Model m = new Model(name, generation, launchYear, finalYear);
        m.setId(model_id);
        return m;
    }

	public int getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(int brand_id) {
		this.brand_id = brand_id;
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
    
}

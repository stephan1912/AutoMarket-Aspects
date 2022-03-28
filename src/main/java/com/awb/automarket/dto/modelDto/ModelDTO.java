package com.awb.automarket.dto.modelDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@CustomDtoClassAnnotation(mappsEntity = Model.class)
public class ModelDTO {
    public int model_id;
    @CustomDtoAnnotation(mappsField = "name")
    public String name;
    @CustomDtoAnnotation(mappsField = "generation")
    public String generation;
    @CustomDtoAnnotation(mappsField = "launchYear")
    public int launchYear;
    @CustomDtoAnnotation(mappsField = "finalYear")
    public int finalYear;


    public ModelDTO(Model model){
        this.model_id = model.getId();
        this.name = model.getName();
        this.generation = model.getGeneration();
        this.launchYear = model.getLaunchYear();
        this.finalYear = model.getFinalYear();
    }

    public Model toModel(){
        Model m = new Model(name, generation, launchYear, finalYear);
        m.setId(model_id);
        return  m;
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

	@Override
	public String toString() {
		return "ModelDTO [model_id=" + model_id + ", name=" + name + ", generation=" + generation + ", launchYear="
				+ launchYear + ", finalYear=" + finalYear + ", toModel()=" + toModel() + ", getModel_id()="
				+ getModel_id() + ", getName()=" + getName() + ", getGeneration()=" + getGeneration()
				+ ", getLaunchYear()=" + getLaunchYear() + ", getFinalYear()=" + getFinalYear() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public ModelDTO(int model_id, String name, String generation, int launchYear, int finalYear) {
		super();
		this.model_id = model_id;
		this.name = name;
		this.generation = generation;
		this.launchYear = launchYear;
		this.finalYear = finalYear;
	}

	public ModelDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

   
}

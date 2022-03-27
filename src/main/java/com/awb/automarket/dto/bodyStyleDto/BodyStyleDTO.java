package com.awb.automarket.dto.bodyStyleDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.BodyStyle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.*;

@Data
@ToString
@CustomDtoClassAnnotation(mappsEntity = BodyStyle.class)
public class BodyStyleDTO {
    public int bs_id;

    @CustomDtoAnnotation(mappsField = "name")
    public String name;

    @CustomDtoAnnotation(mappsField = "description")
    public String description;

    public BodyStyleDTO(int bs_id, String name, String description) {
		super();
		this.bs_id = bs_id;
		this.name = name;
		this.description = description;
	}

	public BodyStyleDTO(BodyStyle bs){
        this.bs_id = bs.getId();
        this.name = bs.getName();
        this.description = bs.getDescription();
    }

    public BodyStyle toBodyStyle(){
        return new BodyStyle(bs_id, name, description, null);
    }

	@Override
	public String toString() {
		return "BodyStyleDTO [bs_id=" + bs_id + ", name=" + name + ", description=" + description + ", toBodyStyle()="
				+ toBodyStyle() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public int getBs_id() {
		return bs_id;
	}

	public void setBs_id(int bs_id) {
		this.bs_id = bs_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BodyStyleDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	

    
}

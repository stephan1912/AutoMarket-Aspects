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
@AllArgsConstructor
@NoArgsConstructor
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
}

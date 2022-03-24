package com.awb.automarket.dto.modelDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
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

    @SneakyThrows
    @Override
    public String toString(){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }
}

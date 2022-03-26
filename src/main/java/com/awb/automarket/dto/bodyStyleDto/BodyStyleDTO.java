package com.awb.automarket.dto.bodyStyleDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.BodyStyle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@CustomDtoClassAnnotation(mappsEntity = BodyStyle.class)
public class BodyStyleDTO {
    public int bs_id;

    @CustomDtoAnnotation(mappsField = "name")
    public String name;

    @CustomDtoAnnotation(mappsField = "description")
    public String description;

    public BodyStyleDTO(BodyStyle bs){
        this.bs_id = bs.getId();
        this.name = bs.getName();
        this.description = bs.getDescription();
    }

    public BodyStyle toBodyStyle(){
        return new BodyStyle(bs_id, name, description, null);
    }

    @SneakyThrows
    @Override
    public String toString(){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }
}

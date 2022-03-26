package com.awb.automarket.dto.brandDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.BodyStyle;
import com.awb.automarket.entity.Brand;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@CustomDtoClassAnnotation(mappsEntity = Brand.class)
public class BrandDTO {
    public int brand_id;
    @CustomDtoAnnotation(mappsField = "name")
    public String name;
    @CustomDtoAnnotation(mappsField = "code")
    public String code;

    public Brand toBrand(){
        Brand b = new Brand(brand_id, name, code, null);
        return b;
    }

    @SneakyThrows
    @Override
    public String toString(){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }
}

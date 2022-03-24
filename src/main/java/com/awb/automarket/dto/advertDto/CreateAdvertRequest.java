package com.awb.automarket.dto.advertDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@CustomDtoClassAnnotation(mappsEntity = Advert.class)
public class CreateAdvertRequest {
    public int advert_id;
    public int country_id;
    public int bodyStyle_id;
    public int user_id;
    public int model_id;
    public List<Integer> features = new ArrayList<>();

    @CustomDtoAnnotation(mappsField = "title")
    public String title;
    @CustomDtoAnnotation(mappsField = "description")
    public String description;

    public Date createdAt;

    @CustomDtoAnnotation(mappsField = "km")
    public int km;
    @CustomDtoAnnotation(mappsField = "year")
    public int year;
    @CustomDtoAnnotation(mappsField = "price")
    public int price;
    public boolean registered;
    public boolean serviceDocs;
    @CustomDtoAnnotation(mappsField = "vin")
    public String vin;
    @CustomDtoAnnotation(mappsField = "horsePower")
    public int horsePower;
    @CustomDtoAnnotation(mappsField = "engineCap")
    public int engineCap;

    public GearboxType gearboxType;
    public Drivetrain drivetrain;
    public Fuel fuel;
}

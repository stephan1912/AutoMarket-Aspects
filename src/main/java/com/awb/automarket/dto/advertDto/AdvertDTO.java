package com.awb.automarket.dto.advertDto;

import com.awb.automarket.dto.bodyStyleDto.BodyStyleDTO;
import com.awb.automarket.dto.commentDto.CommentDTO;
import com.awb.automarket.dto.countryDto.CountryDTO;
import com.awb.automarket.dto.featureDto.FeatureDTO;
import com.awb.automarket.dto.modelDto.ModelResponse;
import com.awb.automarket.entity.Drivetrain;
import com.awb.automarket.entity.Feature;
import com.awb.automarket.entity.Fuel;
import com.awb.automarket.entity.GearboxType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdvertDTO {
    public int advert_id;
    public CountryDTO countryDTO;
    public BodyStyleDTO bodyStyleDTO;
    public int user_id;
    public ModelResponse model;
    public List<CommentDTO> comments;
    public List<FeatureDTO> features;

    public String title;
    public String description;
    public Date createdAt;

    public int km;
    public int year;
    public boolean registered;
    public boolean serviceDocs;
    public String vin;
    public int horsePower;
    public int engineCap;
    public int price;

    public GearboxType gearboxType;
    public Drivetrain drivetrain;
    public Fuel fuel;
    public List<String> pictures;
}

package com.awb.automarket.dto.advertDto;

import com.awb.automarket.entity.GearboxType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class AdvertFilter {
    public int brand;
    public int model;
    public int bs;
    public GearboxType gearbox;
    public int yearMin;
    public int yearMax;
    public int horsePowerMin;
    public int horsePowerMax;;
    public int kmMin;
    public int kmMax;;
    public int priceMin;
    public int priceMax;
}
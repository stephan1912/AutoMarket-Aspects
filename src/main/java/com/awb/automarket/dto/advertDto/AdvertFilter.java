package com.awb.automarket.dto.advertDto;

import com.awb.automarket.entity.GearboxType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
	public int getBrand() {
		return brand;
	}
	public void setBrand(int brand) {
		this.brand = brand;
	}
	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	public int getBs() {
		return bs;
	}
	public void setBs(int bs) {
		this.bs = bs;
	}
	public GearboxType getGearbox() {
		return gearbox;
	}
	public void setGearbox(GearboxType gearbox) {
		this.gearbox = gearbox;
	}
	public int getYearMin() {
		return yearMin;
	}
	public void setYearMin(int yearMin) {
		this.yearMin = yearMin;
	}
	public int getYearMax() {
		return yearMax;
	}
	public void setYearMax(int yearMax) {
		this.yearMax = yearMax;
	}
	public int getHorsePowerMin() {
		return horsePowerMin;
	}
	public void setHorsePowerMin(int horsePowerMin) {
		this.horsePowerMin = horsePowerMin;
	}
	public int getHorsePowerMax() {
		return horsePowerMax;
	}
	public void setHorsePowerMax(int horsePowerMax) {
		this.horsePowerMax = horsePowerMax;
	}
	public int getKmMin() {
		return kmMin;
	}
	public void setKmMin(int kmMin) {
		this.kmMin = kmMin;
	}
	public int getKmMax() {
		return kmMax;
	}
	public void setKmMax(int kmMax) {
		this.kmMax = kmMax;
	}
	public int getPriceMin() {
		return priceMin;
	}
	public void setPriceMin(int priceMin) {
		this.priceMin = priceMin;
	}
	public int getPriceMax() {
		return priceMax;
	}
	public void setPriceMax(int priceMax) {
		this.priceMax = priceMax;
	}
    
    
}
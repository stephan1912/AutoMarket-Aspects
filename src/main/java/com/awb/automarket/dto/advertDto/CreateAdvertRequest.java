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
	public int getAdvert_id() {
		return advert_id;
	}
	public void setAdvert_id(int advert_id) {
		this.advert_id = advert_id;
	}
	public int getCountry_id() {
		return country_id;
	}
	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}
	public int getBodyStyle_id() {
		return bodyStyle_id;
	}
	public void setBodyStyle_id(int bodyStyle_id) {
		this.bodyStyle_id = bodyStyle_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getModel_id() {
		return model_id;
	}
	public void setModel_id(int model_id) {
		this.model_id = model_id;
	}
	public List<Integer> getFeatures() {
		return features;
	}
	public void setFeatures(List<Integer> features) {
		this.features = features;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public int getKm() {
		return km;
	}
	public void setKm(int km) {
		this.km = km;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public boolean isRegistered() {
		return registered;
	}
	public void setRegistered(boolean registered) {
		this.registered = registered;
	}
	public boolean isServiceDocs() {
		return serviceDocs;
	}
	public void setServiceDocs(boolean serviceDocs) {
		this.serviceDocs = serviceDocs;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public int getHorsePower() {
		return horsePower;
	}
	public void setHorsePower(int horsePower) {
		this.horsePower = horsePower;
	}
	public int getEngineCap() {
		return engineCap;
	}
	public void setEngineCap(int engineCap) {
		this.engineCap = engineCap;
	}
	public GearboxType getGearboxType() {
		return gearboxType;
	}
	public void setGearboxType(GearboxType gearboxType) {
		this.gearboxType = gearboxType;
	}
	public Drivetrain getDrivetrain() {
		return drivetrain;
	}
	public void setDrivetrain(Drivetrain drivetrain) {
		this.drivetrain = drivetrain;
	}
	public Fuel getFuel() {
		return fuel;
	}
	public void setFuel(Fuel fuel) {
		this.fuel = fuel;
	}
    
    
}

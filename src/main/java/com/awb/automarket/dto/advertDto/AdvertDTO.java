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

	public int getAdvert_id() {
		return advert_id;
	}
	public void setAdvert_id(int advert_id) {
		this.advert_id = advert_id;
	}
	public CountryDTO getCountryDTO() {
		return countryDTO;
	}
	public void setCountryDTO(CountryDTO countryDTO) {
		this.countryDTO = countryDTO;
	}
	public BodyStyleDTO getBodyStyleDTO() {
		return bodyStyleDTO;
	}
	public void setBodyStyleDTO(BodyStyleDTO bodyStyleDTO) {
		this.bodyStyleDTO = bodyStyleDTO;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public ModelResponse getModel() {
		return model;
	}
	public void setModel(ModelResponse model) {
		this.model = model;
	}
	public List<CommentDTO> getComments() {
		return comments;
	}
	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}
	public List<FeatureDTO> getFeatures() {
		return features;
	}
	public void setFeatures(List<FeatureDTO> features) {
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
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
	public List<String> getPictures() {
		return pictures;
	}
	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}
	public AdvertDTO(int advert_id, CountryDTO countryDTO, BodyStyleDTO bodyStyleDTO, int user_id, ModelResponse model,
			List<CommentDTO> comments, List<FeatureDTO> features, String title, String description, Date createdAt,
			int km, int year, boolean registered, boolean serviceDocs, String vin, int horsePower, int engineCap,
			int price, GearboxType gearboxType, Drivetrain drivetrain, Fuel fuel, List<String> pictures) {
		super();
		this.advert_id = advert_id;
		this.countryDTO = countryDTO;
		this.bodyStyleDTO = bodyStyleDTO;
		this.user_id = user_id;
		this.model = model;
		this.comments = comments;
		this.features = features;
		this.title = title;
		this.description = description;
		this.createdAt = createdAt;
		this.km = km;
		this.year = year;
		this.registered = registered;
		this.serviceDocs = serviceDocs;
		this.vin = vin;
		this.horsePower = horsePower;
		this.engineCap = engineCap;
		this.price = price;
		this.gearboxType = gearboxType;
		this.drivetrain = drivetrain;
		this.fuel = fuel;
		this.pictures = pictures;
	}
	@Override
	public String toString() {
		return "AdvertDTO [advert_id=" + advert_id + ", countryDTO=" + countryDTO + ", bodyStyleDTO=" + bodyStyleDTO
				+ ", user_id=" + user_id + ", model=" + model + ", comments=" + comments + ", features=" + features
				+ ", title=" + title + ", description=" + description + ", createdAt=" + createdAt + ", km=" + km
				+ ", year=" + year + ", registered=" + registered + ", serviceDocs=" + serviceDocs + ", vin=" + vin
				+ ", horsePower=" + horsePower + ", engineCap=" + engineCap + ", price=" + price + ", gearboxType="
				+ gearboxType + ", drivetrain=" + drivetrain + ", fuel=" + fuel + ", pictures=" + pictures + "]";
	}
    
    
}

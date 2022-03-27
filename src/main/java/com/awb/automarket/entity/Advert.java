package com.awb.automarket.entity;

import com.awb.automarket.customvalidation.CustomEntityAnnotation;
import com.awb.automarket.customvalidation.CustomEntityClassAnnotation;
import com.awb.automarket.dto.advertDto.AdvertDTO;
import com.awb.automarket.dto.bodyStyleDto.BodyStyleDTO;
import com.awb.automarket.dto.commentDto.CommentDTO;
import com.awb.automarket.dto.countryDto.CountryDTO;
import com.awb.automarket.dto.featureDto.FeatureDTO;
import com.awb.automarket.dto.modelDto.ModelResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "Advert")
@CustomEntityClassAnnotation(notPresentError = "Anuntul nu exista!")
public class Advert {
    @Id
    @GeneratedValue
    private int id;

    @Column(length = 50, nullable = false)
    @CustomEntityAnnotation(fieldName = "Titlu", min = 10, max = 50, required = true)
    private String title;

    @Column(length = 3000, nullable = false)
    @CustomEntityAnnotation(fieldName = "Descriere", min = 100, max = 3000, required = true)
    private String description;

    @NotNull
    private Date createdAt;

    @NotNull
    @CustomEntityAnnotation(fieldName = "Kilometri", min = 1, max = 9999999, required = true)
    private int km;

    @NotNull
    @CustomEntityAnnotation(fieldName = "An", min = 1900, max = 2021, required = true)
    private int year;

    @NotNull
    private boolean registered;

    @NotNull
    private boolean serviceDocs;

    @NotNull
    @CustomEntityAnnotation(fieldName = "VIN", min = 17, max = 17, required = true)
    private String vin;

    @NotNull
    @CustomEntityAnnotation(fieldName = "Cai putere", min = 1, max = 10000, required = true)
    private int horsePower;

    @NotNull
    @CustomEntityAnnotation(fieldName = "Pret", min = 1, max = 9999999, required = true)
    private int price;

    @NotNull
    @CustomEntityAnnotation(fieldName = "Capacitate cilindrica", min = 1, max = 9999999, required = true)
    private int engineCap;

    @Enumerated(EnumType.STRING)
    @NotNull
    private GearboxType gearboxType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Drivetrain drivetrain;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Fuel fuel;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Model model;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private BodyStyle bodyStyle;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @OneToMany(
            mappedBy = "advert",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(
            mappedBy = "advert",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Images> images = new ArrayList<>();

    public void addComment(Comment comment) {
        comment.setAdvert(this);
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setAdvert(null);
    }

    public void removeFeature(Feature feature) {
        features.remove(feature);
    }

    @ManyToMany
    @JoinTable(name = "advert_features",
            joinColumns =@JoinColumn(name="advert_id",referencedColumnName =
                    "id"),
            inverseJoinColumns
                    =@JoinColumn(name="feature_id",referencedColumnName="id"))
    private List<Feature> features = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advert advert = (Advert) o;
        return id == advert.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public AdvertDTO toDTO(){
        return new AdvertDTO(
                id,
                new CountryDTO(country),
                new BodyStyleDTO(bodyStyle),
                user.getId(),
                new ModelResponse(model),
                comments.stream().map(comment -> new CommentDTO(comment)).collect(Collectors.toList()),
                features.stream().map(feature -> new FeatureDTO(feature)).collect(Collectors.toList()),
                title,
                description,
                createdAt,
                km, 
                year,
                registered,
                serviceDocs,
                vin,
                horsePower,
                engineCap,
                price,
                gearboxType,
                drivetrain,
                fuel,
                images.stream().map(images1 -> images1.getName()).collect(Collectors.toList())
        );
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public BodyStyle getBodyStyle() {
		return bodyStyle;
	}

	public void setBodyStyle(BodyStyle bodyStyle) {
		this.bodyStyle = bodyStyle;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Images> getImages() {
		return images;
	}

	public void setImages(List<Images> images) {
		this.images = images;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

	public Advert(int id, String title, String description, Date createdAt, int km, int year, boolean registered,
			boolean serviceDocs, String vin, int horsePower, int price, int engineCap, GearboxType gearboxType,
			Drivetrain drivetrain, Fuel fuel, Model model, Country country, BodyStyle bodyStyle, User user,
			List<Comment> comments, List<Images> images, List<Feature> features) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.createdAt = createdAt;
		this.km = km;
		this.year = year;
		this.registered = registered;
		this.serviceDocs = serviceDocs;
		this.vin = vin;
		this.horsePower = horsePower;
		this.price = price;
		this.engineCap = engineCap;
		this.gearboxType = gearboxType;
		this.drivetrain = drivetrain;
		this.fuel = fuel;
		this.model = model;
		this.country = country;
		this.bodyStyle = bodyStyle;
		this.user = user;
		this.comments = comments;
		this.images = images;
		this.features = features;
	}

	@Override
	public String toString() {
		return "Advert [id=" + id + ", title=" + title + ", description=" + description + ", createdAt=" + createdAt
				+ ", km=" + km + ", year=" + year + ", registered=" + registered + ", serviceDocs=" + serviceDocs
				+ ", vin=" + vin + ", horsePower=" + horsePower + ", price=" + price + ", engineCap=" + engineCap
				+ ", gearboxType=" + gearboxType + ", drivetrain=" + drivetrain + ", fuel=" + fuel + ", model=" + model
				+ ", country=" + country + ", bodyStyle=" + bodyStyle + ", user=" + user + ", comments=" + comments
				+ ", images=" + images + ", features=" + features + ", hashCode()=" + hashCode() + ", toDTO()="
				+ toDTO() + ", getId()=" + getId() + ", getTitle()=" + getTitle() + ", getDescription()="
				+ getDescription() + ", getCreatedAt()=" + getCreatedAt() + ", getKm()=" + getKm() + ", getYear()="
				+ getYear() + ", isRegistered()=" + isRegistered() + ", isServiceDocs()=" + isServiceDocs()
				+ ", getVin()=" + getVin() + ", getHorsePower()=" + getHorsePower() + ", getPrice()=" + getPrice()
				+ ", getEngineCap()=" + getEngineCap() + ", getGearboxType()=" + getGearboxType() + ", getDrivetrain()="
				+ getDrivetrain() + ", getFuel()=" + getFuel() + ", getModel()=" + getModel() + ", getCountry()="
				+ getCountry() + ", getBodyStyle()=" + getBodyStyle() + ", getUser()=" + getUser() + ", getComments()="
				+ getComments() + ", getImages()=" + getImages() + ", getFeatures()=" + getFeatures() + ", getClass()="
				+ getClass() + ", toString()=" + super.toString() + "]";
	}

	public Advert() {
		super();
		// TODO Auto-generated constructor stub
	}


  

}

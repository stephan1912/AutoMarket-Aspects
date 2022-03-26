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
@AllArgsConstructor
@NoArgsConstructor
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


    @SneakyThrows
    @Override
    public String toString(){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }

}

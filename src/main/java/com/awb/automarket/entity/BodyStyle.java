package com.awb.automarket.entity;

import com.awb.automarket.customvalidation.CustomEntityAnnotation;
import com.awb.automarket.customvalidation.CustomEntityClassAnnotation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BodyStyle")
@CustomEntityClassAnnotation(notPresentError = "Caroseria nu exista!")
public class BodyStyle {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 50, nullable = false)
    @CustomEntityAnnotation(fieldName = "Nume", min = 1, max = 50, required = true)
    private String name;

    @Column(length = 200, nullable = false)
    @CustomEntityAnnotation(fieldName = "Descriere", min = 10, max = 200, required = true)
    private String description;

    @OneToMany(
            mappedBy = "bodyStyle",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Advert> advertList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BodyStyle bodyStyle = (BodyStyle) o;
        return id == bodyStyle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @SneakyThrows
    @Override
    public String toString(){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }
}

package com.awb.automarket.entity;

import com.awb.automarket.customvalidation.CustomEntityAnnotation;
import com.awb.automarket.customvalidation.CustomEntityClassAnnotation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "Feature")
@CustomEntityClassAnnotation(notPresentError = "Optiunea nu exista!")
public class Feature {
    @Id
    @GeneratedValue
    private int id;

    @Column(length = 50, nullable = false)
    @CustomEntityAnnotation(fieldName = "Optiune", min = 2, max = 50, required = true)
    private String name;

    @ManyToMany(mappedBy = "features")
    @JsonIgnore
    private List<Advert> advertList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feature feature = (Feature) o;
        return id == feature.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	@Override
	public String toString() {
		return "Feature [id=" + id + ", name=" + name + ", advertList=" + advertList + ", hashCode()=" + hashCode()
				+ ", getClass()=" + getClass() + ", toString()=" + super.toString() + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Advert> getAdvertList() {
		return advertList;
	}

	public void setAdvertList(List<Advert> advertList) {
		this.advertList = advertList;
	}

	public Feature(int id, String name, List<Advert> advertList) {
		super();
		this.id = id;
		this.name = name;
		this.advertList = advertList;
	}

	public Feature() {
		super();
		// TODO Auto-generated constructor stub
	}

   
}

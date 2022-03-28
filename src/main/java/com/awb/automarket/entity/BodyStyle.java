package com.awb.automarket.entity;

import com.awb.automarket.customvalidation.CustomEntityAnnotation;
import com.awb.automarket.customvalidation.CustomEntityClassAnnotation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "BodyStyle")
@CustomEntityClassAnnotation(notPresentError = "Caroseria nu exista!")
public class BodyStyle {
	
    public BodyStyle(int id, String name, String description, List<Advert> advertList) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.advertList = advertList;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Advert> getAdvertList() {
		return advertList;
	}

	public void setAdvertList(List<Advert> advertList) {
		this.advertList = advertList;
	}


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

	@Override
	public String toString() {
		return "BodyStyle [id=" + id + ", name=" + name + ", description=" + description + ", advertList=" + advertList
				+ ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()=" + super.toString()
				+ "]";
	}

}

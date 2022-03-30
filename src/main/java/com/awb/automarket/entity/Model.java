package com.awb.automarket.entity;

import com.awb.automarket.customvalidation.CustomEntityAnnotation;
import com.awb.automarket.customvalidation.CustomEntityClassAnnotation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Model")
@CustomEntityClassAnnotation(notPresentError = "Modelul nu exista!")
public class Model {

    @Id
    @GeneratedValue
    private int id;
    @Column(length = 50, nullable = false)
    @CustomEntityAnnotation(fieldName = "Nume", min = 1, max = 50, required = true)
    private String name;
    @Column(length = 10, nullable = false)
    @CustomEntityAnnotation(fieldName = "Generatie", min = 1, max = 10, required = true)
    private String generation;
    @CustomEntityAnnotation(fieldName = "An lansare", min = 1900, max = 2021, required = false)
    private int launchYear;
    @CustomEntityAnnotation(fieldName = "An final", min = 1900, max = 2021, required = false)
    private int finalYear;

    public Model(String name, String generation, int launchYear, int finalYear) {
        this.name = name;
        this.generation = generation;
        this.launchYear = launchYear;
        this.finalYear = finalYear;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    public String getName() {
        return name;
    }

    public void setName(String nume) {
        this.name = nume;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generatie) {
        this.generation = generatie;
    }

    public int getLaunchYear() {
        return launchYear;
    }

    public void setLaunchYear(int anLansare) {
        this.launchYear = anLansare;
    }

    public int getFinalYear() {
        return finalYear;
    }

    public void setFinalYear(int anFinal) {
        this.finalYear = anFinal;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return id == model.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	@Override
	public String toString() {
		return "Model [id=" + id + ", name=" + name + ", generation=" + generation + ", launchYear=" + launchYear
				+ ", finalYear=" + finalYear + ", brand=" + brand + ", getName()=" + getName() + ", getGeneration()="
				+ getGeneration() + ", getLaunchYear()=" + getLaunchYear() + ", getFinalYear()=" + getFinalYear()
				+ ", getBrand()=" + getBrand() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass()
				+ ", toString()=" + super.toString() + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Model() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Model(int id, String name, String generation, int launchYear, int finalYear, Brand brand) {
		super();
		this.id = id;
		this.name = name;
		this.generation = generation;
		this.launchYear = launchYear;
		this.finalYear = finalYear;
		this.brand = brand;
	}

		
  
}

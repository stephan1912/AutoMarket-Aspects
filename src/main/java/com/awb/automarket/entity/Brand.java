package com.awb.automarket.entity;

import com.awb.automarket.customvalidation.CustomEntityAnnotation;
import com.awb.automarket.customvalidation.CustomEntityClassAnnotation;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "Brand")
@CustomEntityClassAnnotation(notPresentError = "Brandul nu exista!")
public class Brand {

    @Id
    @GeneratedValue
    private int id;
    @Column(length = 50, nullable = false)
    @CustomEntityAnnotation(fieldName = "Nume", min = 3, max = 50, required = true)
    private String name;
    @CustomEntityAnnotation(fieldName = "Cod", min = 1, max = 5, required = true)
    @Column(length = 5, nullable = false)
    private String code;

    @OneToMany(
            mappedBy = "brand",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Model> modelList = new ArrayList<>();

    public void addModel(Model model){
        model.setBrand(this);
        modelList.add(model);
    }

    public void removeModel(Model model){
        model.setBrand(null);
        modelList.remove(model);
    }

    public void removeModel(Integer modelId){
        Model toRemove = modelList.stream().filter(model -> model.getId() == modelId).findFirst().orElse(null);
        removeModel(toRemove);
    }

    public List<Model> getModelList(){
        return  modelList;
    }

    public String getName() {
        return name;
    }

    public void setName(String nume) {
        this.name = nume;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String prescurtare) {
        this.code = prescurtare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return id == brand.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setModelList(List<Model> modelList) {
		this.modelList = modelList;
	}

	public Brand(int id, String name, String code, List<Model> modelList) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.modelList = modelList;
	}

	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + ", code=" + code + ", modelList=" + modelList
				+ ", getModelList()=" + getModelList() + ", getName()=" + getName() + ", getCode()=" + getCode()
				+ ", hashCode()=" + hashCode() + ", getId()=" + getId() + ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
	}
    

    
}

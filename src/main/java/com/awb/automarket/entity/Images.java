package com.awb.automarket.entity;

import com.sun.istack.NotNull;
import javax.persistence.*;

/**
 * @author Ionut Valentin
 *
 */
@Entity
@Table(name = "Images")
public class Images {
    @Id
    @GeneratedValue
    private int id;
    @Column(length = 150, nullable = false)
    private String name;


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


	public Advert getAdvert() {
		return advert;
	}


	public void setAdvert(Advert advert) {
		this.advert = advert;
	}


	@ManyToOne(fetch = FetchType.LAZY)
    private Advert advert;


	@Override
	public String toString() {
		return "Images [id=" + id + ", name=" + name + ", advert=" + advert + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}


	public Images(int id, String name, Advert advert) {
		super();
		this.id = id;
		this.name = name;
		this.advert = advert;
	}


	public Images() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    
}

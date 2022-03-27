package com.awb.automarket.entity;

import com.awb.automarket.customvalidation.CustomEntityAnnotation;
import com.awb.automarket.customvalidation.CustomEntityClassAnnotation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Data;
import lombok.SneakyThrows;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "Comment")
@CustomEntityClassAnnotation(notPresentError = "Comentariul nu exista!")
public class Comment {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 50, nullable = false)
    @CustomEntityAnnotation(fieldName = "Comentariu", min = 1, max = 50, required = true)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Advert advert;

    public String getComment() {
        return comment;
    }

    public void setComment(String comentariu) {
        this.comment = comentariu;
    }

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment that = (Comment) o;
        return id == that.id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", comment=" + comment + ", user=" + user + ", advert=" + advert
				+ ", getComment()=" + getComment() + ", getAdvert()=" + getAdvert() + ", hashCode()=" + hashCode()
				+ ", getId()=" + getId() + ", getUser()=" + getUser() + ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
	}

	public Comment(int id, String comment, User user, Advert advert) {
		super();
		this.id = id;
		this.comment = comment;
		this.user = user;
		this.advert = advert;
	}

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	
   
}

package com.awb.automarket.entity;

import com.awb.automarket.customvalidation.CustomEntityAnnotation;
import com.awb.automarket.customvalidation.CustomEntityClassAnnotation;
import com.awb.automarket.dto.userDto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "User")
@CustomEntityClassAnnotation(notPresentError = "Utilizatorul nu exista!")
public class User {

    public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20, nullable = false)
    @CustomEntityAnnotation(fieldName = "Nume", min = 5, max = 20, required = true)
    private String lastName;

    @Column(length = 20, nullable = false)
    @CustomEntityAnnotation(fieldName = "Prenume", min = 5, max = 20, required = true)
    private String firstName;

    @Column(length = 50, nullable = false)
    @CustomEntityAnnotation(fieldName = "E-mail", min = 5, max = 50, required = true)
    private String email;

    @Column(length = 13, nullable = false)
    @CustomEntityAnnotation(fieldName = "Parola", min = 6, max = 12, required = true)
    private String password;

    @Column(length = 20, nullable = false)
    @CustomEntityAnnotation(fieldName = "Nume utilizator", min = 5, max = 20, required = true)
    private String username;

    private boolean active;
    private String roles;

    @CustomEntityAnnotation(fieldName = "Data nastere", min = 1900, max = 2015, required = false)
    private Date birthdate;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Advert> advertList = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> commentList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public UserDto toDto(){
        return new UserDto(
                id,
                firstName,
                lastName,
                email,
                username,
                password,
                roles,
                birthdate
        );
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public List<Advert> getAdvertList() {
		return advertList;
	}

	public void setAdvertList(List<Advert> advertList) {
		this.advertList = advertList;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public User(int id, String lastName, String firstName, String email, String password, String username,
			boolean active, String roles, Date birthdate, List<Advert> advertList, List<Comment> commentList) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.username = username;
		this.active = active;
		this.roles = roles;
		this.birthdate = birthdate;
		this.advertList = advertList;
		this.commentList = commentList;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", email=" + email
				+ ", password=" + password + ", username=" + username + ", active=" + active + ", roles=" + roles
				+ ", birthdate=" + birthdate + ", advertList=" + advertList + ", commentList=" + commentList
				+ ", hashCode()=" + hashCode() + ", toDto()=" + toDto() + ", getId()=" + getId() + ", getLastName()="
				+ getLastName() + ", getFirstName()=" + getFirstName() + ", getEmail()=" + getEmail()
				+ ", getPassword()=" + getPassword() + ", getUsername()=" + getUsername() + ", isActive()=" + isActive()
				+ ", getRoles()=" + getRoles() + ", getBirthdate()=" + getBirthdate() + ", getAdvertList()="
				+ getAdvertList() + ", getCommentList()=" + getCommentList() + ", getClass()=" + getClass()
				+ ", toString()=" + super.toString() + "]";
	}


    
}

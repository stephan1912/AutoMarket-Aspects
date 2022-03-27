package com.awb.automarket.dto.userDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.Brand;
import com.awb.automarket.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.*;

import java.util.Date;

@Data
@CustomDtoClassAnnotation(mappsEntity = User.class)
public class UserDto {
    public int id;
    @CustomDtoAnnotation(mappsField = "firstName")
    public String firstName;
    @CustomDtoAnnotation(mappsField = "lastName")
    public String lastName;
    @CustomDtoAnnotation(mappsField = "email")
    public String email;
    @CustomDtoAnnotation(mappsField = "username")
    public String username;
    @CustomDtoAnnotation(mappsField = "password")
    public String password;
    public String roles;
    @CustomDtoAnnotation(mappsField = "birthdate")
    public Date birthdate;

    public User toModel(){
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);
        user.setBirthdate(birthdate);
        return user;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", username=" + username + ", password=" + password + ", roles=" + roles + ", birthdate=" + birthdate
				+ ", toModel()=" + toModel() + ", getId()=" + getId() + ", getFirstName()=" + getFirstName()
				+ ", getLastName()=" + getLastName() + ", getEmail()=" + getEmail() + ", getUsername()=" + getUsername()
				+ ", getPassword()=" + getPassword() + ", getRoles()=" + getRoles() + ", getBirthdate()="
				+ getBirthdate() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public UserDto(int id, String firstName, String lastName, String email, String username, String password,
			String roles, Date birthdate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.birthdate = birthdate;
	}

	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	

  
}

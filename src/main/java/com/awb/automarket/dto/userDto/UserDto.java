package com.awb.automarket.dto.userDto;

import com.awb.automarket.customvalidation.CustomDtoAnnotation;
import com.awb.automarket.customvalidation.CustomDtoClassAnnotation;
import com.awb.automarket.entity.Brand;
import com.awb.automarket.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
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


    @SneakyThrows
    @Override
    public String toString(){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }
}

package com.awb.automarket.entity;

import com.awb.automarket.customvalidation.CustomEntityAnnotation;
import com.awb.automarket.customvalidation.CustomEntityClassAnnotation;
import com.awb.automarket.dto.userDto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "User")
@CustomEntityClassAnnotation(notPresentError = "Utilizatorul nu exista!")
public class User {

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


    @SneakyThrows
    @Override
    public String toString(){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }
}

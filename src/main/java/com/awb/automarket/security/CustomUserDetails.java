package com.awb.automarket.security;

import com.awb.automarket.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Integer id;
    private String userName;
    private String email;
    private String password;
    private boolean active;
    private Set<SimpleGrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.userName = user.getUsername();
        this.password = user.getPassword();
        this.active = user.isActive();
        this.authorities = new HashSet<>();
        Arrays.stream(user.getRoles().split(","))
                .collect(Collectors.toList()).forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_"+role)));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public  Integer getId(){
        return id;
    }

    public String getEmail(){
        return email;
    }
}

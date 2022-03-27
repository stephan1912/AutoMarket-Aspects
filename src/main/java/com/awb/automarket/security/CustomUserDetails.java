package com.awb.automarket.security;

import com.awb.automarket.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;


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

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Set<SimpleGrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
    public String getPassword() {
        return password;
    }

    public CustomUserDetails(Integer id, String userName, String email, String password, boolean active,
			Set<SimpleGrantedAuthority> authorities) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.authorities = authorities;
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

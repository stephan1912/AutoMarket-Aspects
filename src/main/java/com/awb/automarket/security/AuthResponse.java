package com.awb.automarket.security;

public class AuthResponse {
    private String jwt;
    private String email;
    private String username;
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
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
	public AuthResponse(String jwt, String email, String username) {
		super();
		this.jwt = jwt;
		this.email = email;
		this.username = username;
	}
	@Override
	public String toString() {
		return "AuthResponse [jwt=" + jwt + ", email=" + email + ", username=" + username + ", getJwt()=" + getJwt()
				+ ", getEmail()=" + getEmail() + ", getUsername()=" + getUsername() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
    
    
}

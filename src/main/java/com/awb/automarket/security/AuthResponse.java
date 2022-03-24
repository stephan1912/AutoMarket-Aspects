package com.awb.automarket.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class AuthResponse {
    private String jwt;
    private String email;
    private String username;
}

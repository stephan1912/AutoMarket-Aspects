package com.awb.automarket.controller;

import com.awb.automarket.customvalidation.CustomValidator;
import com.awb.automarket.customvalidation.RequireValidation;
import com.awb.automarket.dto.ErrorResponse;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.userDto.PasswordChangeRequest;
import com.awb.automarket.dto.userDto.UserDto;
import com.awb.automarket.entity.User;
import com.awb.automarket.repository.UserRepository;
import com.awb.automarket.security.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/user")
@RestController
public class UserController {

    Logger logger =  LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping()
    @RequireValidation
    public ResponseEntity CreateUser(@RequestBody UserDto user){

        try{
            ServiceResponseModel<UserDto> srm = userDetailsService.save(user);
            if(srm.hasError()){
                return srm.toResponseEntity(logger);
            }

            AuthResponse response = authenticateUser(new AuthRequest(srm.getResponseData().getUsername(), srm.getResponseData().getPassword()));
            if(response == null)
                return ServiceResponseModel.bad(ErrorResponse.InvalidCredentials()).toResponseEntity(logger);
            return ServiceResponseModel.ok(response).toResponseEntity(logger);
        }
        catch (Exception ex){
            return ServiceResponseModel.bad(ErrorResponse.NotValid(ex.getMessage())).toResponseEntity(logger);
        }
    }

    @PutMapping(path = "/me")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity UpdateUser(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UserDto user){

        user.password = "temppass";
        ServiceResponseModel validationResult = CustomValidator.ValidateObject(user);
        user.password = "";
        if(validationResult != null) return validationResult.toResponseEntity(logger);

        user.setId(userDetails.getId());

        ServiceResponseModel<UserDto> srm = userDetailsService.update(user);

        if(srm.hasError()) return srm.toResponseEntity(logger);

        AuthResponse response = authenticateUser(new AuthRequest(srm.getResponseData().getUsername(), srm.getResponseData().getPassword()));
        if(response == null)
            return ServiceResponseModel.bad(ErrorResponse.InvalidCredentials()).toResponseEntity(logger);
        return ServiceResponseModel.ok(response).toResponseEntity(logger);

    }

    @PutMapping(path = "/me/password")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity UpdatePassword(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody PasswordChangeRequest request){
        if(request.getNewPassword().length() < 6 || request.getNewPassword().length() > 12){
            return ServiceResponseModel.StringNotValid("Parola", 6, 12).toResponseEntity(logger);
        }
        if(request.getCurrentPassword().equals(userDetails.getPassword()) == false) return ServiceResponseModel.NotValid("Parola curenta nu este valida!").toResponseEntity(logger);
        if(request.getNewPassword().equals(userDetails.getPassword())) return ServiceResponseModel.NotValid("Parola noua trebuie sa fie diferita de cea veche!").toResponseEntity(logger);

        ServiceResponseModel<UserDto> srm = userDetailsService.updatePassword(userDetails.getId(), request.getNewPassword());

        if(srm.hasError()) return srm.toResponseEntity(logger);

        AuthResponse response = authenticateUser(new AuthRequest(srm.getResponseData().getUsername(), srm.getResponseData().getPassword()));
        if(response == null)
            return ServiceResponseModel.bad(ErrorResponse.InvalidCredentials()).toResponseEntity(logger);
        return ServiceResponseModel.ok(response).toResponseEntity(logger);

    }

    @GetMapping(path = "/email/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity GetUserByEmail(@PathVariable("email") String email){
        return userDetailsService.findbyEmail(email).toResponseEntity(logger);
    }
    @GetMapping(path = "{id}")
    @Authorized(allowedRoles = {"ROLE_ADMIN", "ROLE_USER"})
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity GetUserById(@PathVariable("id") Integer id){
        return userDetailsService.findById(id).toResponseEntity(logger);
    }
    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity DeleteById(@PathVariable("id") Integer id){
        return userDetailsService.deleteById(id).toResponseEntity(logger);
    }

    @PostMapping(path = "/session")
    public ResponseEntity<AuthResponse> CreateSession(@RequestBody AuthRequest authRequest) throws Exception{
        AuthResponse response = authenticateUser(authRequest);
        if(response == null)
            return ServiceResponseModel.bad(ErrorResponse.InvalidCredentials()).toResponseEntity(logger);
        return ServiceResponseModel.ok(response).toResponseEntity(logger);
    }


    @GetMapping(path = "me")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity GetUser(@AuthenticationPrincipal CustomUserDetails userDetails){
        return userDetailsService.findById(userDetails.getId()).toResponseEntity(logger);
    }
    @DeleteMapping(path = "me")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity DeleteById(@AuthenticationPrincipal CustomUserDetails userDetails){
        return userDetailsService.deleteById(userDetails.getId()).toResponseEntity(logger);
    }

    private AuthResponse authenticateUser(AuthRequest authRequest){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e){
            return null;
        }
        final CustomUserDetails userDetails = (CustomUserDetails) userDetailsService
                .loadUserByUsername(authRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return new AuthResponse(jwt, userDetails.getEmail(), userDetails.getUsername());
    }

}

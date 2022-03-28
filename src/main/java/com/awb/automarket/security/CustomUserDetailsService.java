package com.awb.automarket.security;

import com.awb.automarket.dto.ErrorResponse;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.userDto.UserDto;
import com.awb.automarket.entity.User;
import com.awb.automarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService, IUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
        User us = user.get();
        return new CustomUserDetails(us.getId(), us.getUsername(), us.getEmail(), us.getPassword(), us.isActive(), getAuthority(us)); //user.map(CustomUserDetails::new).get();
    }

    private Set getAuthority(User user) {
        Set authorities = new HashSet<>();
        Arrays.stream(user.getRoles().split(","))
                .collect(Collectors.toList()).forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_"+role)));
        return authorities;
    }

    @Override
    public ServiceResponseModel findAll() {
        return ServiceResponseModel.ok(userRepository.findAll().stream().map(user -> user.toDto()));
    }

    @Override
    public ServiceResponseModel findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) return ServiceResponseModel.ok(user.get().toDto());
        return ServiceResponseModel.bad(ErrorResponse.NotFound());
    }

    @Override
    public ServiceResponseModel findbyEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) return ServiceResponseModel.ok(user.get().toDto());
        return ServiceResponseModel.bad(ErrorResponse.NotFound());
    }

    @Override
    public ServiceResponseModel deleteById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) return ServiceResponseModel.bad(ErrorResponse.NotFound());
        userRepository.deleteById(id);
        return ServiceResponseModel.ok(null);
    }

    @Override
    public ServiceResponseModel save(UserDto userDto) {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if(user.isPresent()) return ServiceResponseModel.bad(ErrorResponse.DuplicateError());

        user = userRepository.findByEmail(userDto.getUsername());
        if(user.isPresent()) return ServiceResponseModel.bad(ErrorResponse.DuplicateError());

        userDto.setRoles("USER");

        User toSave = userDto.toModel();
        toSave.setActive(true);
        return ServiceResponseModel.ok((userRepository.save(toSave)).toDto());
    }

    @Override
    public ServiceResponseModel update(UserDto userDto) {
        Optional<User> user = userRepository.findById(userDto.getId());
        if(user.isEmpty()) return ServiceResponseModel.bad(ErrorResponse.NotFound());
        User fromBd = user.get();
        user = userRepository.findByUsername(userDto.getUsername());
        if(user.isPresent() && user.get().getId() != userDto.getId()) return ServiceResponseModel.bad(ErrorResponse.DuplicateError());

        user = userRepository.findByEmail(userDto.getEmail());
        if(user.isPresent() && user.get().getId() != userDto.getId()) return ServiceResponseModel.bad(ErrorResponse.DuplicateError());

        if(!userDto.getFirstName().isEmpty()) fromBd.setFirstName(userDto.getFirstName());
        if(!userDto.getLastName().isEmpty()) fromBd.setLastName(userDto.getLastName());
        if(!userDto.getUsername().isEmpty()) fromBd.setUsername(userDto.getUsername());
        if(!userDto.getEmail().isEmpty()) fromBd.setEmail(userDto.getEmail());
        if(userDto.getBirthdate() != null) fromBd.setBirthdate(userDto.getBirthdate());

        return ServiceResponseModel.ok((userRepository.save(fromBd)).toDto());
    }

    @Override
    public ServiceResponseModel updatePassword(Integer id, String newPassword) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) return ServiceResponseModel.bad(ErrorResponse.NotFound());
        User fromBd = user.get();
        fromBd.setPassword(newPassword);
        return ServiceResponseModel.ok((userRepository.save(fromBd)).toDto());
    }
}
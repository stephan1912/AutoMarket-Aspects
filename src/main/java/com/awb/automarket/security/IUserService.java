package com.awb.automarket.security;

import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.userDto.UserDto;

public interface IUserService {
    ServiceResponseModel findAll();
    ServiceResponseModel findById(Integer id);
    ServiceResponseModel findbyEmail(String email);
    ServiceResponseModel deleteById(Integer id);
    ServiceResponseModel save(UserDto userDto);
    ServiceResponseModel update(UserDto userDto);
    ServiceResponseModel updatePassword(Integer id, String newPassword);
}

package com.awb.automarket.dto.userDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PasswordChangeRequest {
    public String newPassword;
    public String currentPassword;


    @SneakyThrows
    @Override
    public String toString(){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(this);
    }
}

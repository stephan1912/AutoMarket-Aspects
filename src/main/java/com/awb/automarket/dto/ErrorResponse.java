package com.awb.automarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    public int code;
    public int httpCode;
    public String message;

    public static ErrorResponse DuplicateError() {return new ErrorResponse(4009, 409, "Un obiect similar exista deja.");}
    public static ErrorResponse NotFound() {return new ErrorResponse(4004, 404, "Resursa cautata nu exista.");}
    public static ErrorResponse UnknownError() {return new ErrorResponse(5000, 500, "A aparut o eroare, va rugam sa reincercati.");}
    public static ErrorResponse NotValid() {return new ErrorResponse(4000, 400, "Cererea nu este valida.");}
    public static ErrorResponse InvalidCredentials() {return new ErrorResponse(4001, 401, "Credentialele folosite nu sunt corecte.");}



    public static ErrorResponse DuplicateError(String message) {return new ErrorResponse(4109, 409, message);}
    public static ErrorResponse NotFound(String message) {return new ErrorResponse(4104, 404, message);}
    public static ErrorResponse Unauthorized() {return new ErrorResponse(4101, 401, "Acces interzis.");}
    public static ErrorResponse NotValid(String message) {return new ErrorResponse(4100, 400, message);}
}

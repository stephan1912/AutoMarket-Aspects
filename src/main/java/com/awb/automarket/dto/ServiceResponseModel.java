package com.awb.automarket.dto;

import com.awb.automarket.customvalidation.CustomEntityClassAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;


@Data
public class ServiceResponseModel<T> {

    public T responseData;
    public ErrorResponse errorResponse;
    

    public ServiceResponseModel(T responseData, ErrorResponse errorResponse) {
		super();
		this.responseData = responseData;
		this.errorResponse = errorResponse;
	}
	public static ServiceResponseModel ok(Object object) {
        return  new ServiceResponseModel(object, null);
    };
    public static ServiceResponseModel bad(ErrorResponse err) {
        return  new ServiceResponseModel(null, err);
    };
    public static ServiceResponseModel bad(Object object, ErrorResponse err) {
        return  new ServiceResponseModel(object, err);
    };


    public static ServiceResponseModel Conflict(String message){ return ServiceResponseModel.bad(ErrorResponse.DuplicateError(message)); }

    public static ServiceResponseModel NotFound(String message){ return ServiceResponseModel.bad(ErrorResponse.NotFound(message)); }

    public static ServiceResponseModel NotValid(String message){ return ServiceResponseModel.bad(ErrorResponse.NotValid(message)); }


    public static ServiceResponseModel StringNotValid(String field, Integer maxChar){ return ServiceResponseModel.bad(ErrorResponse.NotValid("Campul '" + field + "' trebuie sa contine cel mult " + maxChar + " de caractere.")); }

    public static ServiceResponseModel StringNotValid(String field, Integer maxChar, Integer minChar){
        if(maxChar == minChar){
            return ServiceResponseModel.bad(ErrorResponse.NotValid("Campul '" + field + "' trebuie sa aiba exact " + minChar + " caractere."));
        }
        return ServiceResponseModel.bad(ErrorResponse.NotValid("Campul '" + field + "' trebuie sa aiba intre " + minChar + " si " + maxChar + " caractere."));
    }

    public static ServiceResponseModel IntegerNotValid(String field, Integer max){ return ServiceResponseModel.bad(ErrorResponse.NotValid("Campul '" + field + "' poate fi maxim " + max + ".")); }

    public static ServiceResponseModel IntegerNotValid(String field, Integer max, Integer min){
        return ServiceResponseModel.bad(ErrorResponse.NotValid("Campul '" + field + "' trebuie sa fie in intervalul " + min + " si " + max + "."));
    }

    public static ServiceResponseModel InvalidDate(String field, Integer max, Integer min){ return ServiceResponseModel.bad(ErrorResponse.NotValid("Campul '" + field + "' trebuie aiba anul in intervalul " + min + " si " + max + ".")); }


    public static ServiceResponseModel ClassNotFound(Class cls){
        CustomEntityClassAnnotation annotation = (CustomEntityClassAnnotation) cls.getAnnotation(CustomEntityClassAnnotation.class);
        if(annotation == null) return ServiceResponseModel.NotFound("Obiectul nu a fost gasit");

        return ServiceResponseModel.NotFound(annotation.notPresentError());
    }

    public static ServiceResponseModel Unauthorized(){ return ServiceResponseModel.bad(ErrorResponse.Unauthorized()); }

    public static ServiceResponseModel InvalidCredentials(){ return ServiceResponseModel.bad(ErrorResponse.InvalidCredentials()); }

    public boolean hasError(){
        return errorResponse != null;
    }

    public ResponseEntity toResponseEntity(Logger logger){
        if(errorResponse != null){
            logger.error("ErrorMessage: " + errorResponse.message +  "\t ErrorCode: " + errorResponse.code + "\t HttpCode: " + errorResponse.httpCode);
            return ResponseEntity.status(errorResponse.getHttpCode()).body(errorResponse);
        }
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity toResponseEntity(){
        if(errorResponse != null){
            return ResponseEntity.status(errorResponse.getHttpCode()).body(errorResponse);
        }
        return ResponseEntity.ok(responseData);
    }
	public T getResponseData() {
		return responseData;
	}
	public void setResponseData(T responseData) {
		this.responseData = responseData;
	}
	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}
	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}
    
    

}

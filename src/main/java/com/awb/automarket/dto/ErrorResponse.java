package com.awb.automarket.dto;


public class ErrorResponse {

    public int code;
    public int httpCode;
    public String message;

    public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getHttpCode() {
		return httpCode;
	}
	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ErrorResponse(int code, int httpCode, String message) {
		super();
		this.code = code;
		this.httpCode = httpCode;
		this.message = message;
	}
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

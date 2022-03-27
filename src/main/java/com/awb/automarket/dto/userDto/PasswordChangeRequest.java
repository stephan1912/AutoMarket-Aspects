package com.awb.automarket.dto.userDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.*;

@Data
public class PasswordChangeRequest {
    public String newPassword;
    public String currentPassword;
	@Override
	public String toString() {
		return "PasswordChangeRequest [newPassword=" + newPassword + ", currentPassword=" + currentPassword
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

   
}

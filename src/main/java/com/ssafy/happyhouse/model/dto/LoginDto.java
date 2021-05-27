package com.ssafy.happyhouse.model.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value="로그인에 필요한 정보",description="이메일, 비밀번호")
public class LoginDto {
	private String email;
	private String password;
	public LoginDto(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
}

package com.ssafy.happyhouse.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "회원 정보",description = "회원의 고유 번호, 이름, 패스워드, 이메일, 주소, 등급을 확인할 수 있음")
public class MemberDto {
	@ApiModelProperty(value = "회원 고유 코드")
	private int code;
	@ApiModelProperty(value = "이름")
	private String name;
	@ApiModelProperty(value = "패스워드")
	private String password;
	@ApiModelProperty(value = "이메일")
	private String email;
	@ApiModelProperty(value = "주소")
	private String address;
	@ApiModelProperty(value = "등급")
	private int level;
	@ApiModelProperty(value = "계정 생성 시각")
	private String regdate;
	@ApiModelProperty(value = "이메일 인증값")
	private String certified;
	
	public MemberDto() {}
	public MemberDto(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	public MemberDto(int code, String name, String password, String email, String address, int level,
			String regdate) {
		super();
		this.code = code;
		this.name = name;
		this.password = password;
		this.email = email;
		this.address = address;
		this.level = level;
		this.regdate = regdate;
	}

	public String getCertified() {
		return certified;
	}

	public void setCertified(String certified) {
		this.certified = certified;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
}
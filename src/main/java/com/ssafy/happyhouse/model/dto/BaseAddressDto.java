package com.ssafy.happyhouse.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="BaseAddressDto : 기본주소",description = "각 지역(구,동)별 주소 데이터")
public class BaseAddressDto {
	private int no;
	@ApiModelProperty(value="시 명")
	private String city;// baseaddress의 고유 식별 코드
	@ApiModelProperty(value="고유 식별 코드")
	private String code;
	@ApiModelProperty(value="동 코드")
	private String dongcode;
	@ApiModelProperty(value="구군명")
	private String gugun;
	@ApiModelProperty(value="동명")
	private String dong;
	@ApiModelProperty(value="위도")
	private String lat;
	@ApiModelProperty(value="경도")
	private String lng;

	public BaseAddressDto() {
	}

	public BaseAddressDto(int no, String city, String code, String dongcode, String gugun, String dong, String lat,
			String lng) {
		super();
		this.no = no;
		this.city = city;
		this.code = code;
		this.dongcode = dongcode;
		this.gugun = gugun;
		this.dong = dong;
		this.lat = lat;
		this.lng = lng;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDongcode() {
		return dongcode;
	}

	public void setDongcode(String dongcode) {
		this.dongcode = dongcode;
	}

	public String getGugun() {
		return gugun;
	}

	public void setGugun(String gugun) {
		this.gugun = gugun;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
}
package com.ssafy.happyhouse.model.dto;

public class InterestAreaDto {
	private int usercode;
	private String areacode;
	private String city;
	private String dongcode;
	private String gugun;
	private String dong;
	private String lat;
	private String lng;
	public InterestAreaDto(int usercode, String areacode, String city, String dongcode, String gugun, String dong,
			String lat, String lng) {
		super();
		this.usercode = usercode;
		this.areacode = areacode;
		this.city = city;
		this.dongcode = dongcode;
		this.gugun = gugun;
		this.dong = dong;
		this.lat = lat;
		this.lng = lng;
	}
	public int getUsercode() {
		return usercode;
	}
	public void setUsercode(int usercode) {
		this.usercode = usercode;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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

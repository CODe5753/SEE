package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.dto.HouseInfoDto;


public interface HouseService {
	
	public List<HouseInfoDto> getlist();
	
	public List<HouseInfoDto> getInteresetBuilding(int member_code);
	
	public List<HouseInfoDto> searchlist(Map<String, String>map);
	
	public HouseInfoDto detail(int id);
	
	public List<HouseInfoDto> specificzone(Map<String, String> map);
	
	
}

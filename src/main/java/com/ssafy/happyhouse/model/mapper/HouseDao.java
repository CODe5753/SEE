package com.ssafy.happyhouse.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.dto.HouseInfoDto;

@Mapper
public interface HouseDao {
	
	public List<HouseInfoDto> getlist();
	
	public List<HouseInfoDto> searchlist(Map<String, String>map);
	
	public List<HouseInfoDto> getInteresetBuilding(int member_code);
	
	public HouseInfoDto detail(int id);
	
	public List<HouseInfoDto> specificzone(Map<String, String> map);
	
}

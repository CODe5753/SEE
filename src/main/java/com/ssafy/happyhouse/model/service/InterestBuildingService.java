package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.dto.InterestBuildingDto;

public interface InterestBuildingService {
	
	public List<InterestBuildingDto> getInteresetBuilding(int member_code);

	public boolean deleteInterestBuilding(InterestBuildingDto interestbuildingDto);

	public boolean addInterestBuilding(InterestBuildingDto interestbuildingDto);


}

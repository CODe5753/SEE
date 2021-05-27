package com.ssafy.happyhouse.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.dto.InterestBuildingDto;

public interface InterestBuildingDao {
	
		// 현재 사용자의 관심 건물 정보를 모두 가져옴
		public List<InterestBuildingDto> getInteresetBuilding(int member_code);

		public boolean deleteInterestBuilding(InterestBuildingDto interestbuildingDto);

		public boolean addInterestBuilding(InterestBuildingDto interestbuildingDto);

}

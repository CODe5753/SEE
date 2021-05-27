package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.dto.BaseAddressDto;

@Mapper
public interface InterestAreaDao {

	// 현재 사용자의 관심 지역 정보를 모두 가져옴
	public List<BaseAddressDto> getInteresetArea(int usercode);

	public boolean deleteInterestArea(int usercode, String basecode);

	public boolean addInterest(int usercode, String basecode);// 현재 사용자가 관심 지역 정보를 추가함

}

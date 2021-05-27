package com.ssafy.happyhouse.model.service;

import java.util.List;


import com.ssafy.happyhouse.model.dto.BaseAddressDto;


public interface InterestAreaService {
	
	//현재 사용자의 관심 지역 정보를 모두 가져옴
	public List<BaseAddressDto> getInteresetArea(int code);
	
	public boolean deleteInterestArea(int usercode,String basecode);
	
	public boolean addInterest(int usercode,String basecode);//현재 사용자가 관심 지역 정보를 추가함
	
}

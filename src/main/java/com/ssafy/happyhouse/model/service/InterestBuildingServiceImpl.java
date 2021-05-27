package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dto.InterestBuildingDto;
import com.ssafy.happyhouse.model.mapper.HouseDao;
import com.ssafy.happyhouse.model.mapper.InterestBuildingDao;

@Service
public class InterestBuildingServiceImpl implements InterestBuildingService {

	@Autowired
	private SqlSession SqlSession;
	
	@Override
	public List<InterestBuildingDto> getInteresetBuilding(int member_code) {
		return SqlSession.getMapper(InterestBuildingDao.class).getInteresetBuilding(member_code);
	}

	@Override
	public boolean deleteInterestBuilding(InterestBuildingDto interestbuildingDto) {
		return SqlSession.getMapper(InterestBuildingDao.class).deleteInterestBuilding(interestbuildingDto);
	}

	@Override
	public boolean addInterestBuilding(InterestBuildingDto interestbuildingDto) {
		return SqlSession.getMapper(InterestBuildingDao.class).addInterestBuilding(interestbuildingDto);
	}



}

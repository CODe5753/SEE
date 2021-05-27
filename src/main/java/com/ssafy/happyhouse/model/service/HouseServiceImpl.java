package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dto.HouseInfoDto;
import com.ssafy.happyhouse.model.mapper.HouseDao;

@Service
public class HouseServiceImpl implements HouseService {
	
	@Autowired
	private SqlSession SqlSession;

	@Override
	public List<HouseInfoDto> getlist() {
		return SqlSession.getMapper(HouseDao.class).getlist();
	}

	@Override
	public List<HouseInfoDto> searchlist(Map<String, String>map) {
		//select : 전체 / 동 / 아파트명 등등 .. search : select에 맞는 검색명
		return SqlSession.getMapper(HouseDao.class).searchlist(map);
	}

	@Override
	public HouseInfoDto detail(int id) {	
		return SqlSession.getMapper(HouseDao.class).detail(id);
	}

	@Override
	public List<HouseInfoDto> specificzone(Map<String, String> map) {
		return SqlSession.getMapper(HouseDao.class).specificzone(map);
	}

	@Override
	public List<HouseInfoDto> getInteresetBuilding(int member_code) {
		return SqlSession.getMapper(HouseDao.class).getInteresetBuilding(member_code);
	}

}

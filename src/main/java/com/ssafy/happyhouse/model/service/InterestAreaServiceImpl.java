package com.ssafy.happyhouse.model.service;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dto.BaseAddressDto;
import com.ssafy.happyhouse.model.dto.InterestAreaDto;
import com.ssafy.happyhouse.model.mapper.InterestAreaDao;


@Service
public class InterestAreaServiceImpl implements InterestAreaService{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<BaseAddressDto> getInteresetArea(int usercode) {		
		return sqlSession.getMapper(InterestAreaDao.class).getInteresetArea(usercode);
	}

	@Override
	public boolean deleteInterestArea(int usercode, String basecode) {
		return sqlSession.getMapper(InterestAreaDao.class).deleteInterestArea(usercode, basecode);
	}

	@Override
	public boolean addInterest(int usercode, String basecode) {
		return sqlSession.getMapper(InterestAreaDao.class).addInterest(usercode, basecode);		
	}
	
}

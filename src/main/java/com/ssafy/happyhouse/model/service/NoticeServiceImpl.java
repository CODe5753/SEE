package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dto.NoticeDto;
import com.ssafy.happyhouse.model.mapper.NoticeDao;

@Service
public class NoticeServiceImpl implements NoticeService {

	
	@Autowired
	private SqlSession SqlSession;
	
	@Override
	public boolean regist(NoticeDto noticeDto) {
		return SqlSession.getMapper(NoticeDao.class).regist(noticeDto);
	}

	@Override
	public List<NoticeDto> searchlist(String title) {
		return SqlSession.getMapper(NoticeDao.class).searchlist(title);
	}

	@Override
	public NoticeDto select(int no) {
		return SqlSession.getMapper(NoticeDao.class).select(no);
	}

	@Override
	public boolean remove(int no) {
		return SqlSession.getMapper(NoticeDao.class).remove(no);
	}

	@Override
	public boolean modify(NoticeDto noticeDto) {
		return SqlSession.getMapper(NoticeDao.class).modify(noticeDto);
	}

	@Override
	public List<NoticeDto> getlist() {
		return SqlSession.getMapper(NoticeDao.class).getlist();
	}
	
}

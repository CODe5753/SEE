package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dto.QnaCommentDto;
import com.ssafy.happyhouse.model.dto.QnaDto;
import com.ssafy.happyhouse.model.mapper.QnaDao;

@Service
public class QnaServiceImpl implements QnaService {
	
	@Autowired
	private SqlSession SqlSession;
	
	@Override
	public List<QnaDto> getlist() {
		return SqlSession.getMapper(QnaDao.class).getlist();
	}

	@Override
	public QnaDto getquestion(int id) {
		return SqlSession.getMapper(QnaDao.class).getquestion(id);
	}

	@Override
	public boolean remove(int id) {
		return SqlSession.getMapper(QnaDao.class).remove(id);
	}

	@Override
	public boolean update(QnaDto qnaDto) {
		return SqlSession.getMapper(QnaDao.class).update(qnaDto);
	}

	@Override
	public boolean regist(QnaDto qnaDto) {
		return SqlSession.getMapper(QnaDao.class).regist(qnaDto);
	}

	@Override
	public List<QnaDto> getanswer(int id) {
		return SqlSession.getMapper(QnaDao.class).getanswer(id);
	}

	@Override
	public List<QnaDto> searchlist(Map<String, String> map) {
		//select : 전체, 작성자, 내용, 제목, search: select에 맞는 검색명
		return SqlSession.getMapper(QnaDao.class).searchlist(map);
	}

	@Override
	public boolean registcomment(QnaCommentDto qnacommentDto) {
		return SqlSession.getMapper(QnaDao.class).registcomment(qnacommentDto);
	}

	@Override
	public List<QnaCommentDto> getcommentlist(int id) {
		return SqlSession.getMapper(QnaDao.class).getcommentlist(id);
	}

	@Override
	public boolean updatecomment(QnaCommentDto qnacommentDto) {
		return SqlSession.getMapper(QnaDao.class).updatecomment(qnacommentDto);
	}

	@Override
	public boolean removecomment(int id) {
		return SqlSession.getMapper(QnaDao.class).removecomment(id);
	}

	@Override
	public List<QnaDto> getmyqna(String id) {
		return SqlSession.getMapper(QnaDao.class).getmyqna(id);
	}

}

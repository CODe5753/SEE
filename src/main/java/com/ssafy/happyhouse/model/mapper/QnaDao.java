package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.dto.QnaCommentDto;
import com.ssafy.happyhouse.model.dto.QnaDto;

@Mapper
public interface QnaDao {
	
	public List<QnaDto> getlist();
	
	public List<QnaDto> getanswer(int id);
	
	public QnaDto getquestion(int id);
	
	public List<QnaDto> searchlist(Map<String, String>map);
	
	public List<QnaDto> getmyqna(String id);
	
	public boolean remove(int id);
	
	public boolean update(QnaDto qnaDto);
	
	public boolean regist(QnaDto qnaDto);
	
	//댓글
	public boolean registcomment(QnaCommentDto qnacommentDto);
	
	public List<QnaCommentDto> getcommentlist(int id);
	
	public boolean updatecomment(QnaCommentDto qnacommentDto);
	
	public boolean removecomment(int id);

}

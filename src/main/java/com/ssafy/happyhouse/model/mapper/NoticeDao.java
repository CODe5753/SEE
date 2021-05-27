package com.ssafy.happyhouse.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.dto.NoticeDto;

@Mapper
public interface NoticeDao {

	public boolean regist(NoticeDto noticeDto);

	public List<NoticeDto> searchlist(String title);

	public NoticeDto select(int no);

	public boolean remove(int no);

	public boolean modify(NoticeDto noticeDto);

	public List<NoticeDto> getlist();

}

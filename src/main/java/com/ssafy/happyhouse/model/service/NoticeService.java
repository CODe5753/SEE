package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.dto.NoticeDto;

public interface NoticeService {

	public boolean regist(NoticeDto noticeDto);
	
	public List<NoticeDto> searchlist(String title);
	
	public NoticeDto select(int no);

	public boolean remove(int no);

	public boolean modify(NoticeDto noticeDto);

	public List<NoticeDto> getlist();
}

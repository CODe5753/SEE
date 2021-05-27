package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.dto.InfoDto;


public interface InfoService {

	public void register(InfoDto noticeDto);
	
	public List<InfoDto> search(String title);
	
	public InfoDto select(int no);
	
	public void remove(int no);
	
	public void modify(InfoDto noticeDto);
	
	public List<InfoDto> selectAll();
}

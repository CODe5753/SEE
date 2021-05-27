package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import com.ssafy.happyhouse.model.dto.InfoDto;

public interface InfoDao {

	public void register(InfoDto noticeDto);

	public List<InfoDto> search(String title);

	public InfoDto select(int no);

	public void remove(int no);

	public void modify(InfoDto noticeDto);

	public List<InfoDto> selectAll();
}

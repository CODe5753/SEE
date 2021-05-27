package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.happyhouse.model.dto.MemberDto;
import com.ssafy.happyhouse.model.entity.Member;

@Mapper
public interface MemberDao{
	
	public MemberDto login(Map<String, String>map) throws SQLException;

	public boolean passUpdate(Map<String, String>map) throws SQLException;

	public boolean remove(String email) throws SQLException;

	public boolean register(Map<String, String>map) throws SQLException;

	public MemberDto memberinfo(String code);

	public boolean memberUpdate(Map<String, String>map);

	public List<MemberDto> memberList();
	
	public boolean adminUpdate(Map<String,String>map);
	
	public MemberDto pwfind(Map<String, String>map) throws SQLException;
}
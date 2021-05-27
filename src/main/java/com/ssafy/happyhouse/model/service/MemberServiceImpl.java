package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dto.MemberDto;
import com.ssafy.happyhouse.model.mapper.MemberDao;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private SqlSession SqlSession;

	@Override
	public MemberDto login(Map<String, String>map) throws SQLException {
		if(map.get("email").equals("") || map.get("password").equals("")) {
			return null;
		}
		return SqlSession.getMapper(MemberDao.class).login(map);
	}

	@Override
	public boolean passUpdate(Map<String, String>map) throws SQLException {
		// originPwd , originPwd2, newPwd, newPwd2
		System.out.println("여기오나?");
		return SqlSession.getMapper(MemberDao.class).passUpdate(map);
	}

	@Override
	public boolean remove(String email) throws SQLException {
		return SqlSession.getMapper(MemberDao.class).remove(email);
	}

	@Override
	public boolean register(Map<String, String>map) throws SQLException {
		if(map.get("name").equals("") || map.get("address").equals("") || map.get("email").equals("") ||
				map.get("password").equals("") || map.get("checkpwd").equals("") || !(map.get("password").equals(map.get("checkpwd")))) return false;
		
		else return SqlSession.getMapper(MemberDao.class).register(map);
	}

	@Override
	public MemberDto memberinfo(String code) {
		return SqlSession.getMapper(MemberDao.class).memberinfo(code);
	}

	@Override
	public boolean memberUpdate(Map<String, String>map){
		
		if(map.get("name") == null || map.get("address")== null) return false;
		else return SqlSession.getMapper(MemberDao.class).memberUpdate(map);
	}

	@Override
	public List<MemberDto> memberList() {
		return SqlSession.getMapper(MemberDao.class).memberList();
	}

	//관리자 전용 수정
	@Override
	public boolean adminUpdate(Map<String, String> map) {
		if(map.get("name").equals("") || map.get("address").equals("")||map.get("password").equals("")||map.get("level").equals("")) return false;
		else return SqlSession.getMapper(MemberDao.class).adminUpdate(map);
	}

	@Override
	public MemberDto pwfind(Map<String, String> map) throws SQLException {
		if(map.get("name").equals("") || map.get("email").equals("")) return null;
		else return SqlSession.getMapper(MemberDao.class).pwfind(map);
	}
}

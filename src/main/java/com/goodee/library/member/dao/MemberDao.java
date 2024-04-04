package com.goodee.library.member.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.goodee.library.member.dto.MemberDto;

@Repository
public class MemberDao { 
	
	private static final Logger LOGGER =
			LogManager.getLogger(MemberDao.class);
	
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private final String NAMESPACE = "com.goodee.library.memberMapper.";
	
	public int idDoubleCheck(String m_id) {
		LOGGER.info("아이디 중복 검사");
		int result = 0;
		try {
			result = sqlSession.selectOne(NAMESPACE + "idDoubleCheck", m_id);
		} catch (Exception e) {
			// 오류 로그 string 으로 찍기 
			StringWriter errors = new StringWriter();
	        e.printStackTrace(new PrintWriter(errors));
	        LOGGER.error(errors.toString());
		}
		return result;
	}
	
	public int createMember(MemberDto memberDto) {
		LOGGER.info("회원정보 데이터베이스 추가");
		int result = 0;
		try {
			memberDto.setM_pw(passwordEncoder.encode(memberDto.getM_pw()));
			result = sqlSession.insert(NAMESPACE+"createMember",memberDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public MemberDto selectMember(MemberDto memberDto) {
		LOGGER.info("아이디 기준 멤버 1명 조회");
		MemberDto loginMember = new MemberDto();
		try {
			loginMember = sqlSession.selectOne(NAMESPACE+"selectMember", memberDto.getM_id());
			if(loginMember!= null && passwordEncoder.matches(memberDto.getM_pw(), loginMember.getM_id())) {
				loginMember = null;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return loginMember;
	}

	public List<MemberDto> selectMemberAll() {
		LOGGER.info("회원 목록 조회");
		List<MemberDto> resultList = new ArrayList<MemberDto>();
		try {
			resultList = sqlSession.selectList(NAMESPACE+"selectMemberAll");
		}catch(Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LOGGER.error(errors.toString());
		}
		return resultList;
	}

	public int updateMember(MemberDto memberDto) {
		int success = 0;
		try {
			success = sqlSession.update(NAMESPACE+"updateMember", memberDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public MemberDto selectUpdateMember(MemberDto memberDto) {
		MemberDto loginMember = new MemberDto();
		try {
			loginMember = sqlSession.selectOne(NAMESPACE+"selectNewMember", memberDto.getM_no());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginMember;
	}
	
}

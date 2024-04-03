package com.goodee.library.member.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
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
		LOGGER.info("아이디 기준으로 멤버 조회");
		MemberDto loginDto = new MemberDto();
		try {
			loginDto = sqlSession.selectOne(NAMESPACE+"selectMember", memberDto.getM_id());
			if(loginDto != null) {
				// 비밀번호 일치여부 확인
				if(!passwordEncoder.matches(memberDto.getM_pw(), loginDto.getM_pw())) {
					loginDto = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginDto;
	}
	
}

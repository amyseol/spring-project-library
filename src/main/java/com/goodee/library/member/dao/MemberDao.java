package com.goodee.library.member.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goodee.library.member.dto.MemberDto;

@Repository
public class MemberDao { 
	
	private static final Logger LOGGER =
			LogManager.getLogger(MemberDao.class);
	
	@Autowired
	private SqlSession sqlSession;
	
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
	
	// return 값을 Integer 로 하면 null 값을 받아온다.
	public int createMember(MemberDto memberDto) {
		int result = 0;
		
		return result;
	}
	
}

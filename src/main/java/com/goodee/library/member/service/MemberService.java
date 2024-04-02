package com.goodee.library.member.service;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.library.member.dao.MemberDao;
import com.goodee.library.member.dto.MemberDto;

@Service
public class MemberService {
	
	private static final Logger LOGGER =
			LogManager.getLogger(MemberService.class);
	
	@Autowired
	MemberDao memberDao;
	
	public Map<String, String> createMember(MemberDto memberDto) {
		LOGGER.info("회원 가입 결과 처리");
		int result = 0;
		if(memberDao.idDoubleCheck(memberDto.getM_id()) > 0) {
			
		}else {
			result = memberDao.createMember(memberDto);
			if(result > 0) {
				// 회원가입 성공 
			}else {
				// 회원가입 실패
			}
		}
		return null;
	}
	
}

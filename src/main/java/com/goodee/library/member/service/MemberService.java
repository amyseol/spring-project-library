package com.goodee.library.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
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
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "404");
		map.put("res_msg", "오류가 발생했습니다");
		
		int result = 0;
		if(memberDao.idDoubleCheck(memberDto.getM_id()) > 0) {
			map.put("res_code", "409");
			map.put("res_msg", "중복된 아이디 입니다.");
		}else {
			result = memberDao.createMember(memberDto);
			if(result > 0) {
				map.put("res_code", "200");
			}
		}
		return map;
	}

	public Map<String,String> loginMember(MemberDto memberDto, HttpSession session){
		LOGGER.info("로그인 결과 처리");
		Map<String,String> map = new HashMap<String,String>();
		map.put("res_code", "404");
		map.put("res_msg", "오류가 발생했습니다.");
		
		MemberDto loginMember = memberDao.selectMember(memberDto);
		
		if(loginMember != null) {
			if(loginMember.getM_flag().equals("Y")) {
				session.setAttribute("loginMember", loginMember);
				map.put("res_code", "200");
				map.put("res_msg", loginMember.getM_name()+"님, 환영합니다.");
			}else {
				map.put("res_code", "409");
				map.put("res_msg", "이미 탈퇴한 회원입니다.");
			}
		}
		return map;
	}

	public List<MemberDto> selectMemberAll() {
		LOGGER.info("회원 목록 조회 요청");
		return memberDao.selectMemberAll();
	}

	public Map<String, String> updateMember(MemberDto memberDto, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "400");
		map.put("res_msg", "회원 정보 수정중 오류가 발생하였습니다.");
		
		if(memberDao.updateMember(memberDto) > 0) {
			LOGGER.info("DB 수정 성공!");
			MemberDto updateMemberDto = memberDao.selectMemberByNo(memberDto);
			session.setAttribute("loginMember", updateMemberDto);
			session.setMaxInactiveInterval(60*30);
			map.put("res_code", "200");
			map.put("res_msg", "회원 정보가 수정되었습니다.");	
		}
		return map;
	}

	public Map<String, String> deleteMember(long memberNo, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("res_code", "400");
		map.put("res_msg", "회원 탈퇴 중 오류가 발생하였습니다.");
		
		if(memberDao.deleteMember(memberNo) > 0) {	
			map.put("res_code", "200");
			map.put("res_msg", "탈퇴가 완료되었습니다.");	
			session.invalidate();
		}
		return map;
	}
	
}

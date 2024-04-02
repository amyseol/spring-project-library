package com.goodee.library.member.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.library.member.dto.MemberDto;
import com.goodee.library.member.service.MemberService;

@Controller
public class MemberApiController {
	
	private static final Logger LOGGER =
			LogManager.getLogger(MemberApiController.class);
	
	@Autowired
	MemberService memberService;
	
	@PostMapping("/join")
	@ResponseBody
	public Map<String, String> joinMember(@RequestBody MemberDto memberDto) {
		LOGGER.info("회원가입 기능");
		return memberService.createMember(memberDto);
	}
	
}

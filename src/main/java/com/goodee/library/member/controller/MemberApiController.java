package com.goodee.library.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@ResponseBody
	@PostMapping("/join")
	public Map<String, String> joinMember(@RequestBody MemberDto memberDto) {
		LOGGER.info("회원가입 기능");
		return memberService.createMember(memberDto);
	}
	
	@ResponseBody
	@PostMapping("/login")
	public Map<String, String> login(@RequestBody MemberDto memberDto, HttpSession session) {
		LOGGER.info("로그인 기능");
		return memberService.loginMember(memberDto, session);
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		LOGGER.info("로그아웃 기능");
		session.invalidate();
		return "redirect:/";
	}
	
	@PostMapping("/member/{m_no}")
	@ResponseBody
	public Map<String, String> memberEdit(@RequestBody MemberDto memberDto, 
			HttpSession session) {
		LOGGER.info("회원 정보 수정 기능");
		return memberService.updateMember(memberDto, session);
	}
	
}

package com.goodee.library.member.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.goodee.library.member.dto.MemberDto;
import com.goodee.library.member.service.MemberService;

@Controller
public class MemberViewController {
	
	private static final Logger LOGGER =
			LogManager.getLogger(MemberViewController.class);
	
	@Autowired
	MemberService memberService;
	
	@GetMapping("/join")
	public String openJoin() {
		LOGGER.info("회원가입 화면으로 이동!");
		return "member/join";
	}
	
	@GetMapping("/login")
	public String openLogin() {
		LOGGER.info("로그인 화면으로 이동!");
		return "member/login";
	}
	
	@GetMapping("/member")
	public String selectMemberAll(Model model) {
		LOGGER.info("회원 목록 화면으로 이동!");
		List<MemberDto> resultList = new ArrayList<MemberDto>();
		resultList = memberService.selectMemberAll();
		model.addAttribute("resultList", resultList);
		return "member/list";
	}
	
	@GetMapping("/member/{m_no}")
	public String editMember(@PathVariable("m_no") long memberNo, HttpSession session) {
		LOGGER.info("회원 수정 화면 이동");
		return "member/edit";
	}

}

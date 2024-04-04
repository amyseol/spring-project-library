package com.goodee.library.member.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.goodee.library.member.dto.MemberDto;

public class MemberLoginInterceptor implements HandlerInterceptor {

	@Override 
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// client 요청이 서버에 도착하기 전(controller 들어오기 전)에 동작 (로그인 여부 체크 등)
		HttpSession session = request.getSession();
		if(session != null) {
			MemberDto memberDto = (MemberDto) session.getAttribute("loginMember");
			if(memberDto != null) {
				return true;
			}
		}
		response.sendRedirect(request.getContextPath()+"/login");
		return false;
	}
	
	@Override 
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 서버가 요청을 처리하고(controller 실행 후) view로 전달하기 전에 동작 (로그 남기기 등) 
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override 
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// client 요청이 서버에서 실행되고 view 에 응답 완료 후 동작 (웹소켓 사용시 비동기 통신 정상 동작 여부 확인 등)
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
}

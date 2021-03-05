package com.ljh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ljh.model.MemberVO;
import com.ljh.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	private MemberService memberService;
	
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public void joinGET() {
		logger.info("회원가입 페이지 진입");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void loginGET() {
		logger.info("로그인 페이지 진입");
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinPOST(MemberVO memberVO) throws Exception{
		memberService.memberJoin(memberVO); // 회원가입 서비스 실행.
		logger.info("회원가입 성공");
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/memberIdChk", method = RequestMethod.POST)
	@ResponseBody
	public String memberIdChkPOST(String memberId) throws Exception{
		logger.info("memberId : " + memberId);
		int result = memberService.idCheck(memberId);
		logger.info("결과값 : " + result);
		if (result != 0) {
			return "fail"; // 중복 아이디가 존재.
		}else {
			return "success"; // 중복 아이디가 없음.
		}
	}
}

package com.ljh.controller;

import java.util.Random;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ljh.model.MemberVO;
import com.ljh.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	private MemberService memberService;
	private JavaMailSender mailSender;
	
	@Autowired
	public MemberController(MemberService memberService, JavaMailSender mailSender) {
		this.memberService = memberService;
		this.mailSender = mailSender;
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
	
	@RequestMapping(value = "/mailCheck", method = RequestMethod.GET)
	@ResponseBody
	public String mailCheckGET(String email) throws Exception{
		// 인증번호 난수 생성.
		Random random = new Random();
		int checkNum = random.nextInt(888888) + 111111;
		logger.info("인증번호 : " + checkNum);
		
		// 이메일로 인증번호 보내기.
		String setFrom = "ljh53502@naver.com";
		String toMail = email;
		String title = "회원가입 인증메일입니다.";
		String content = 
				"홈페이지를 방문해주셔서 감사합니다." +
				"<br><br>" + 
				"인증 번호는 " + checkNum + "입니다." + 
				"<br>" + 
				"해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content, true);
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String num = Integer.toString(checkNum);
		return num;
	}
}

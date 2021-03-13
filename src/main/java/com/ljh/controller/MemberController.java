package com.ljh.controller;

import java.util.Random;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ljh.model.MemberVO;
import com.ljh.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
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
		String rawPw = ""; // 인코딩 전 비밀번호.
		String encodePw = ""; // 인코딩 후 비밀번호.
		
		rawPw = memberVO.getMemberPw(); // 비밀번호 데이터 받음.
		encodePw = passwordEncoder.encode(rawPw); // 비밀번호 인코딩.
		memberVO.setMemberPw(encodePw); // 인코딩된 비밀번호를 memberVO 객체에 다시 저장.
		
		memberService.memberJoin(memberVO); // 회원가입 서비스 실행.
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
	
	// 로그인.
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String loginPOST(HttpServletRequest request, MemberVO memberVO, RedirectAttributes rttr) throws Exception{
		HttpSession session = request.getSession();
		String rawPw = "";
		String encodePw = "";	
		
		MemberVO lvo = memberService.memberLogin(memberVO); // 제출한 아이디와 일치하는 아이디가 있는지.
		
		if (lvo != null) { // 일치하지 않는 아이디 존재 시.
			rawPw = memberVO.getMemberPw(); // 사용자가 제출한 비밀번호.
			encodePw = lvo.getMemberPw(); // 데이터베이스에 저장한 인코딩된 비밀번호.
			
			if (true == passwordEncoder.matches(rawPw, encodePw)) { // 비밀번호 일치여부 판단.
				lvo.setMemberPw(""); // 인코딩된 비밀번호 정보 지움.
				session.setAttribute("member", lvo); // 세션에 사용자 정보 저장.
				return "redirect:/main"; // 메인 페이지 이동.
			}else {
				rttr.addFlashAttribute("result", 0);
				return "redirect:/member/login"; // 로그인 페이지로 이동.
			}
		}else { // 일치하는 아이디가 존재하지 않을 시. (로그인 실패)
			rttr.addFlashAttribute("result", 0);
			return "redirect:/member/login"; // 로그인 페이지로 이동.
		}
	}
	
	// 로그아웃.
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logoutMainGET(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/main";
	}
	
	// 비동기방식 로그아웃 메서드. 
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public void logoutPOST(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        session.invalidate();
    }
}
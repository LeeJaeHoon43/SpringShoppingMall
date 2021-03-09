package com.ljh.mapper;

import com.ljh.model.MemberVO;

public interface MemberMapper {
	// 회원가입.
	public void memberJoin(MemberVO memberVO);
	
	// 아이디 중복 검사.
	public int idCheck(String memberId);
	
	// 로그인.
	public MemberVO memberLogin(MemberVO memberVO);
}

package com.ljh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ljh.mapper.MemberMapper;
import com.ljh.model.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{

	private MemberMapper memberMapper;

	@Autowired
	public MemberServiceImpl(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	@Override
	public void memberJoin(MemberVO memberVO) throws Exception {
		memberMapper.memberJoin(memberVO);
	}

	@Override
	public int idCheck(String memberId) throws Exception {
		return memberMapper.idCheck(memberId);
	}
}

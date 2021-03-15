package com.ljh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ljh.mapper.AuthorMapper;
import com.ljh.model.AuthorVO;

@Service
public class AuthorServiceImpl implements AuthorService{
	
	private AuthorMapper authorMapper;

	@Autowired
	public AuthorServiceImpl(AuthorMapper authorMapper) {
		this.authorMapper = authorMapper;
	}
	
	@Override
	public void authorEnroll(AuthorVO author) throws Exception {
		authorMapper.authorEnroll(author);
	}
}

package com.ljh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ljh.mapper.AuthorMapper;
import com.ljh.model.AuthorVO;
import com.ljh.model.Criteria;

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

	@Override
	public List<AuthorVO> authorGetList(Criteria cri) throws Exception {
		return authorMapper.authorGetList(cri);
	}

	@Override
	public int authorGetTotal(Criteria cri) throws Exception {
		return authorMapper.authorGetTotal(cri);
	}

	@Override
	public AuthorVO authorGetDetail(int authorId) throws Exception {
		return authorMapper.authorGetDetail(authorId);
	}

	@Override
	public int authorModify(AuthorVO author) throws Exception {
		return authorMapper.authorModify(author);
	}
}

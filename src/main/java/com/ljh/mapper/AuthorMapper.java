package com.ljh.mapper;

import java.util.List;
import com.ljh.model.AuthorVO;
import com.ljh.model.Criteria;

public interface AuthorMapper {

	// 작가 등록.
    public void authorEnroll(AuthorVO author);
    
    // 작가 목록.
    public List<AuthorVO> authorGetList(Criteria cri);
    
    // 작가 총 수.
    public int authorGetTotal(Criteria cri);
    
    // 작가 상세.
    public AuthorVO authorGetDetail(int authorId);
    
    // 작가 정보 수정.
    public int authorModify(AuthorVO author);
}

package com.ljh.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ljh.model.AuthorVO;
import com.ljh.model.Criteria;
import com.ljh.model.PageDTO;
import com.ljh.service.AuthorService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	private AuthorService authorService;

	@Autowired
	public AdminController(AuthorService authorService) {
		this.authorService = authorService;
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public void adminMainGET() throws Exception{
		
	}
	
	/* 상품 등록 페이지 접속 */
    @RequestMapping(value = "/goodsManage", method = RequestMethod.GET)
    public void goodsManageGET() throws Exception{
        logger.info("상품 등록 페이지 접속");
    }
    
    /* 상품 등록 페이지 접속 */
    @RequestMapping(value = "/goodsEnroll", method = RequestMethod.GET)
    public void goodsEnrollGET() throws Exception{
        logger.info("상품 등록 페이지 접속");
    }
    
    /* 작가 등록 페이지 접속 */
    @RequestMapping(value = "/authorEnroll", method = RequestMethod.GET)
    public void authorEnrollGET() throws Exception{
        logger.info("작가 등록 페이지 접속");
    }
    
    /* 작가 관리 페이지 접속 */
    @RequestMapping(value = "/authorManage", method = RequestMethod.GET)
    public void authorManageGET(Criteria cri, Model model) throws Exception{
        logger.info("작가 관리 페이지 접속");
        
        // 작가 목록 출력 데이터.
        List list = authorService.authorGetList(cri);
		
		if(!list.isEmpty()) {
			model.addAttribute("list",list);	// 작가 존재 경우
		} else {
			model.addAttribute("listCheck", "empty");	// 작가 존재하지 않을 경우
		}
        
        // 페이지 이동 인터페이스 데이터.
        model.addAttribute("pageMaker", new PageDTO(cri, authorService.authorGetTotal(cri)));
    } 
    
    // 작가 등록.
    @RequestMapping(value = "/authorEnroll.do", method = RequestMethod.POST)
    public String authorEnrollPOST(AuthorVO author, RedirectAttributes rttr) throws Exception{
    	authorService.authorEnroll(author); // 작가 등록 쿼리 수정.
    	rttr.addFlashAttribute("enroll_result", author.getAuthorName()); // 등록 성공 메시지(작가 이름)
    	return "redirect:/admin/authorManage";
    }
    
    // 작가 상세 페이지.
    @RequestMapping(value = {"/authorDetail", "/authorModify"})
    public void authorGetInfoGET(int authorId, Criteria cri, Model model) throws Exception{
    	// 작가 관리 페이지 정보.
    	model.addAttribute("cri", cri);
    	
    	// 선택 작가 정보.
    	model.addAttribute("authorInfo", authorService.authorGetDetail(authorId));
    }
    
    // 작가 정보 수정.
    @PostMapping("/authorModify") 
    public String authorModifyPOST(AuthorVO author, RedirectAttributes rttr) throws Exception{ 
    	int result = authorService.authorModify(author); 
    	rttr.addFlashAttribute("modify_result", result); 
    	return "redirect:/admin/authorManage"; 
    }
}

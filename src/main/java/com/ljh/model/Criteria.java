package com.ljh.model;

public class Criteria {
	
	private int pageNum; // 현재 페이지 번호.
	private int amount; // 페이지 표시 개수.
	private String type; // 검색 타입.
	private String keyword; // 검색 키워드.
	
	// 기본 생성자.
	public Criteria() {
		this(1, 10);
	}
	
	// 생성자.
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	// 검색 타입 데이터 배열 반환.
	public String[] getTypeArr() {
		return type == null ? new String[] {} : type.split("");
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "Criteria [pageNum=" + pageNum + ", amount=" + amount + ", type=" + type + ", keyword=" + keyword + "]";
	}
}

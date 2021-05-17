package com.web.blog;

public class PostException extends RuntimeException{

	public PostException() {
		super("게시물 처리 중 오류 발생하였습니다.");
	}

	public PostException(String msg) {
		super(msg);
	}
}

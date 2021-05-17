package com.web.blog;

public class UserException extends RuntimeException{

	public UserException() {
		super("유저 정보를 처리 중 오류 발생하였습니다.");
	}

	public UserException(String msg) {
		super(msg);
	}
}

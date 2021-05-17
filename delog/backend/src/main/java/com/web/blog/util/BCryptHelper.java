package com.web.blog.util;

public interface BCryptHelper {
	String encrpyt(String password);
	
	boolean isMatch(String password, String hashed);
}

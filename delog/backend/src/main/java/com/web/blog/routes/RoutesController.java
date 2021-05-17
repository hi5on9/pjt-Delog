package com.web.blog.routes;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoutesController implements ErrorController {

	/** Controller에서 모든 uri에 대하여 index.html로 향하게 하는 함수 */
	@GetMapping("/error")
	public String error() {
		return "index.html";
	}
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
}
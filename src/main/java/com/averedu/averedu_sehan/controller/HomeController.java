package com.averedu.averedu_sehan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@GetMapping(value = "/")
	public String index() {
		 // return "redirect:/index.html";
		return "index.html";
	}
	
	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
}

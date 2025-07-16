package com.averedu.averedu_sehan.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.averedu.averedu_sehan.service.login.LoginService;
import com.averedu.averedu_sehan.vo.com.SessionVO;
import com.averedu.averedu_sehan.vo.user.UserVO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/restApi")
public class LoginController {
	
	@Autowired
	LoginService loginService;

	@PostMapping("/login")
	 public UserVO login(@RequestBody UserVO userVO, HttpSession session) {
		UserVO loginVO = loginService.login(userVO);
		if(loginVO.getStatusCd() == 200) {
			SessionVO sessionVO = new SessionVO();
		}
		return loginVO;
	 }
}

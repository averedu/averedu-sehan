package com.averedu.averedu_sehan.prj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.averedu.averedu_sehan.prj.com.vo.SessionVO;
import com.averedu.averedu_sehan.prj.sys.service.LoginService;
import com.averedu.averedu_sehan.prj.sys.vo.UserVO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/restApi/prj/sys")
public class LoginController {
	
	@Autowired
	LoginService loginService;

	@PostMapping("/login")
	 public UserVO login(@RequestBody UserVO userVO, HttpSession session) {
		UserVO loginVO = loginService.login(userVO);

		if(loginVO.getStatusCd() == 200) {
			SessionVO sessionVO = new SessionVO();
			sessionVO.setLoginId(loginVO.getLoginId());
			session.setAttribute("sessionInfo", sessionVO);
		}
		return loginVO;
	 }
}

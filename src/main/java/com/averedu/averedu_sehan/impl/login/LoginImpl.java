package com.averedu.averedu_sehan.impl.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.averedu.averedu_sehan.mapper.login.LoginMapper;
import com.averedu.averedu_sehan.service.login.LoginService;
import com.averedu.averedu_sehan.vo.user.UserVO;

@Service
public class LoginImpl implements LoginService{
	
	@Autowired
	private LoginMapper loginMapper;
	
	@Override
	public UserVO login(UserVO userVO) {
		UserVO loginVO = loginMapper.login(userVO);
		
		
		
		try {
			if(loginVO != null) {
				loginVO.setStatusCd(200);
				loginVO.setStatusMsg("성공");
				return loginVO;
			}
		}catch (Exception e) {
			loginVO.setStatusCd(500);
			loginVO.setStatusMsg("로그인 중 오류 발생 [" + e.getMessage() + "]");
		}
		return loginVO;
	}
}

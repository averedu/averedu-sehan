package com.averedu.averedu_sehan.service.login;

import org.springframework.stereotype.Service;

import com.averedu.averedu_sehan.vo.user.UserVO;

@Service
public interface LoginService {
	public UserVO login(UserVO userVO);
}

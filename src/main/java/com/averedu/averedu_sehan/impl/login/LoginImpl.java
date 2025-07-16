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
		    if(loginVO != null){
		        if(loginVO.getLoginId() != null && loginVO.getPwd() != null){
		            loginVO.setStatusCd(200);
		            loginVO.setStatusMsg("성공");
		            return loginVO;
		        } else {
		            loginVO.setStatusCd(404);
		            loginVO.setStatusMsg("로그인 정보가 없습니다.");
		        }
		    } else {
		        // loginMapper.login() 결과가 null 인 경우 참조값을 생성 후 상태코드, 상태 메시지를 넣어준다
		        loginVO = new UserVO();
		        loginVO.setStatusCd(404);
		        loginVO.setStatusMsg("로그인 정보가 없습니다.");
		    }
		} catch (Exception e) {
		    if(loginVO == null) {
		    	loginVO = new UserVO();
		    }
		    loginVO.setStatusCd(500);
		    loginVO.setStatusMsg("로그인 중 오류 발생\n[" + e.getMessage() + "]");
		}

		return loginVO;
	}
}

package com.averedu.averedu_sehan.mapper.login;

import org.apache.ibatis.annotations.Mapper;

import com.averedu.averedu_sehan.vo.user.UserVO;

@Mapper
public interface LoginMapper {
	public UserVO login(UserVO uservo);
}

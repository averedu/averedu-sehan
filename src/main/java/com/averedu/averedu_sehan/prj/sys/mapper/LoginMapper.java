package com.averedu.averedu_sehan.prj.sys.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.averedu.averedu_sehan.prj.sys.vo.UserVO;

@Mapper
public interface LoginMapper {
	public UserVO login(UserVO uservo);
}

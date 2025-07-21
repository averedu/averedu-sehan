package com.averedu.averedu_sehan.prj.sys.vo;

import com.averedu.averedu_sehan.prj.com.vo.StatusVO;

import lombok.Data;

@Data
public class UserVO extends StatusVO{
	private String loginId;
	private String pwd;
}

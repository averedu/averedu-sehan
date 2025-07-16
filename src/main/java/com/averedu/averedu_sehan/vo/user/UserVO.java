package com.averedu.averedu_sehan.vo.user;

import lombok.Data;
import com.averedu.averedu_sehan.vo.com.StatusVO;

@Data
public class UserVO extends StatusVO{
	private String loginId;
	private String pwd;
}

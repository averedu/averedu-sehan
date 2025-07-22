package com.averedu.averedu_sehan.prj.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.averedu.averedu_sehan.prj.sys.vo.Csys210VO;

@Mapper
public interface CommonMenuMapper {
	List<Csys210VO> selectCommonMenuList(Map<String, Object> param);
	String menuKeyCode();
	void insertMenu(Csys210VO vo);
	void updateMenu(Csys210VO vo);
	void deleteMenu(Csys210VO vo);
}

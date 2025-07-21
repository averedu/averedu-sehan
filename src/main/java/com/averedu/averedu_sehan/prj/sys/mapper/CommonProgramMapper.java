package com.averedu.averedu_sehan.prj.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.averedu.averedu_sehan.prj.sys.vo.Csys200VO;

@Mapper
public interface CommonProgramMapper {
	List<Csys200VO> selectCommonProgramList(Map<String, Object> param);
	String programKeyCode();
	void insertProgram(Csys200VO vo);
	void updateProgram(Csys200VO vo);
	void deleteProgram(Csys200VO vo);
}

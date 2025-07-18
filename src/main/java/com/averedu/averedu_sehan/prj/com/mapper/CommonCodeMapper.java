package com.averedu.averedu_sehan.prj.com.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.averedu.averedu_sehan.prj.com.vo.Csys300VO;

@Mapper
public interface CommonCodeMapper {
    List<Csys300VO> selectDeptCdMngList(Map<String, Object> param); // 부서코드 목록 조회
}

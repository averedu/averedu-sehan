package com.averedu.averedu_sehan.prj.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.averedu.averedu_sehan.prj.sys.vo.Csys130VO;

@Mapper
public interface CommonUserMapper {
    List<Csys130VO> selectCommonUserList(Map<String, Object> param);
}

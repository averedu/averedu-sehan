package com.averedu.averedu_sehan.prj.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.averedu.averedu_sehan.prj.sys.vo.Csys130VO;

@Mapper
public interface CommonUserMapper {
    List<Csys130VO> selectCommonUserList(Map<String, Object> param); // 사용자 목록 조회
    void insertCsys130Q01(Csys130VO vo); // 사용자 정보 등록
    void updateCsys130Q01(Csys130VO vo); // 사용자 정보 수정
    void deleteCsys130Q01(Csys130VO vo); // 사용자 정보 삭제
}

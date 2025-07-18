package com.averedu.averedu_sehan.prj.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.averedu.averedu_sehan.prj.sys.mapper.CommonUserMapper;
import com.averedu.averedu_sehan.prj.sys.vo.Csys130VO;

@Service
public class CommonUserService {

    @Autowired
    private CommonUserMapper commonUserMapper;

    public List<Csys130VO> selectCommonUserList(Map<String, Object> param) {
        return commonUserMapper.selectCommonUserList(param);
    }

    public ResponseEntity<?> saveCommonUserList(List<Csys130VO> saveRows) {
        try {
            for (Csys130VO vo : saveRows) {
                switch (vo.getStatus()) {
                    case "I": // 신규
                        commonUserMapper.insertCsys130Q01(vo);
                        break;
                    case "U": // 수정
                        commonUserMapper.updateCsys130Q01(vo);
                        break;
                    case "D": // 삭제
                        commonUserMapper.deleteCsys130Q01(vo);
                        break;
                }
            }
            return ResponseEntity.ok("저장 성공");
        } catch (Exception e) {
            // 에러 메시지와 함께 500 에러 반환
            return ResponseEntity.status(500).body("저장 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}

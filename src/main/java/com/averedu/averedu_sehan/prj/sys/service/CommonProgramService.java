package com.averedu.averedu_sehan.prj.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.averedu.averedu_sehan.prj.sys.mapper.CommonProgramMapper;
import com.averedu.averedu_sehan.prj.sys.vo.Csys130VO;
import com.averedu.averedu_sehan.prj.sys.vo.Csys200VO;

@Service
public class CommonProgramService {

	@Autowired
	CommonProgramMapper commonProgramMapper;
	
	public List<Csys200VO> selectCommonProgramList(Map<String, Object> param){
		List<Csys200VO> selectCommonProgramList = commonProgramMapper.selectCommonProgramList(param);
		return selectCommonProgramList;
	}
	
   public ResponseEntity<?> saveCommonProgramList(List<Csys200VO> saveRows){
	   try {
           for (Csys200VO vo : saveRows) {
               switch (vo.getStatus()) {
                   case "I": // 신규
                	   String programKeyCode = commonProgramMapper.programKeyCode();
                	   System.out.println("programKeyCode : " + programKeyCode);
                	   vo.setPgmId(programKeyCode);
                       commonProgramMapper.insertProgram(vo);
                       break;
                   case "U": // 수정
                       commonProgramMapper.updateProgram(vo);
                       break;
                   case "D": // 삭제
                       commonProgramMapper.deleteProgram(vo);
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

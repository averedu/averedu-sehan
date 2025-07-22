package com.averedu.averedu_sehan.prj.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.averedu.averedu_sehan.prj.sys.mapper.CommonMenuMapper;
import com.averedu.averedu_sehan.prj.sys.vo.Csys200VO;
import com.averedu.averedu_sehan.prj.sys.vo.Csys210VO;

@Service
public class CommonMenuService {
	@Autowired
	CommonMenuMapper commonMenuMapper;
	
	public List<Csys210VO> selectCommonMenuList(Map<String, Object> param){
		List<Csys210VO> selectCommonMenuList = commonMenuMapper.selectCommonMenuList(param);
		return selectCommonMenuList;
	}
	
	public ResponseEntity<?> saveCommonMenuList(List<Csys210VO> saveRows){
		   try {
	           for (Csys210VO vo : saveRows) {
	               switch (vo.getStatus()) {
	                   case "I": // 신규
	                	   String menuKeyCode = commonMenuMapper.menuKeyCode();
	                	   System.out.println("menuKeyCode : " + menuKeyCode);
	                	   vo.setMenuId(menuKeyCode);
	                	   commonMenuMapper.insertMenu(vo);
	                       break;
	                   case "U": // 수정
	                	   commonMenuMapper.updateMenu(vo);
	                       break;
	                   case "D": // 삭제
	                	   commonMenuMapper.deleteMenu(vo);
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

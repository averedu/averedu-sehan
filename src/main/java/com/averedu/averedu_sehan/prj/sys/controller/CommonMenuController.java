package com.averedu.averedu_sehan.prj.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.averedu.averedu_sehan.prj.sys.service.CommonMenuService;
import com.averedu.averedu_sehan.prj.sys.vo.Csys210VO;

@RestController
@RequestMapping("/restApi/prj/sys")
public class CommonMenuController {
	
	 @Autowired
	 private CommonMenuService commonMenuService;

	 @PostMapping("/selectCommonMenuList")
	 public List<Csys210VO> getMenuList(@RequestBody Map<String, Object> param) {
	     return commonMenuService.selectCommonMenuList(param);
	 }
	 
	 @PostMapping("/saveCommonMenuList")
	 public ResponseEntity<?> saveCommonProgramList(@RequestBody List<Csys210VO> saveRows){
		 return commonMenuService.saveCommonMenuList(saveRows);
	 }

}

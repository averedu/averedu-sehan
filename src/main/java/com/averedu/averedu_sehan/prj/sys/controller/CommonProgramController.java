package com.averedu.averedu_sehan.prj.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.averedu.averedu_sehan.prj.sys.service.CommonProgramService;
import com.averedu.averedu_sehan.prj.sys.vo.Csys200VO;

@RestController
@RequestMapping("/restApi/prj/sys")
public class CommonProgramController {
	@Autowired
	CommonProgramService commonProgramService;
	
	@PostMapping("/selectCommonProgramList")
	public List<Csys200VO> getProgramList(@RequestBody Map<String, Object> param){
		List<Csys200VO> selectCommonProgramList = commonProgramService.selectCommonProgramList(param);
		return selectCommonProgramList;
	}
	
	@PostMapping("/saveCommonProgramList")
	public ResponseEntity<?> saveCommonProgramList(@RequestBody List<Csys200VO> saveRows){
		return commonProgramService.saveCommonProgramList(saveRows);
	}
	
}

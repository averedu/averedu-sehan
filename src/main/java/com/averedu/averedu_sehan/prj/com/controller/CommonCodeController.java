package com.averedu.averedu_sehan.prj.com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.averedu.averedu_sehan.prj.com.service.CommonCodeService;
import com.averedu.averedu_sehan.prj.com.vo.Csys300VO;

@RestController
@RequestMapping("/restApi/prj/com")
public class CommonCodeController {

    @Autowired
    private CommonCodeService commonCodeService;

    @PostMapping("/selectDeptCdMngList.do")
    public List<Csys300VO> getDeptCdMngList(@RequestBody Map<String, Object> param) {
        return commonCodeService.selectDeptCdMngList(param);
    }
}

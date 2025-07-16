package com.averedu.averedu_sehan.prj.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.averedu.averedu_sehan.prj.sys.service.CommonUserService;
import com.averedu.averedu_sehan.prj.sys.vo.Csys130VO;

@RestController
@RequestMapping("/restApi/prj/sys/commonUser")
public class CommonUserController {
    @Autowired
    private CommonUserService commonUserService;

    @PostMapping("/selectCommonUserList.do")
    public List<Csys130VO> getUserList(@RequestBody Map<String, Object> param) {
        return commonUserService.selectCommonUserList(param);
    }
}

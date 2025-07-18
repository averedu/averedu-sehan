package com.averedu.averedu_sehan.prj.com.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.averedu.averedu_sehan.prj.com.mapper.CommonCodeMapper;
import com.averedu.averedu_sehan.prj.com.vo.Csys300VO;

@Service
public class CommonCodeService {

    @Autowired
    private CommonCodeMapper commonCodeMapper;

    public List<Csys300VO> selectDeptCdMngList(Map<String, Object> param) {
        return commonCodeMapper.selectDeptCdMngList(param);
    }
}

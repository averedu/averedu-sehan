package com.averedu.averedu_sehan.prj.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
}

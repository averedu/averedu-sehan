package com.averedu.averedu_sehan.prj.sys.vo;

import lombok.Data;

@Data
public class Csys130VO {
    private String loginId;
    private String pwd;
    private String pwdErrFreq;
    private String pwdChgDttm;
    private String userStCd;
    private String flLoginDttm;
    private String persNo;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwdErrFreq() {
        return pwdErrFreq;
    }

    public void setPwdErrFreq(String pwdErrFreq) {
        this.pwdErrFreq = pwdErrFreq;
    }

    public String getPwdChgDttm() {
        return pwdChgDttm;
    }

    public void setPwdChgDttm(String pwdChgDttm) {
        this.pwdChgDttm = pwdChgDttm;
    }

    public String getUserStCd() {
        return userStCd;
    }

    public void setUserStCd(String userStCd) {
        this.userStCd = userStCd;
    }

    public String getFlLoginDttm() {
        return flLoginDttm;
    }

    public void setFlLoginDttm(String flLoginDttm) {
        this.flLoginDttm = flLoginDttm;
    }

    public String getPersNo() {
        return persNo;
    }

    public void setPersNo(String persNo) {
        this.persNo = persNo;
    }
}

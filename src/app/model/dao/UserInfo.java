package app.model.dao;

import app.model.vo.LoginVO;

import java.util.List;

public class UserInfo {
    private List<LoginVO> lltv = null;

    public void setUserInfo(LoginVO ltv) {
        lltv.add(ltv);
    }

    public List<LoginVO> getUserInfo() {
        return lltv;
    }
}

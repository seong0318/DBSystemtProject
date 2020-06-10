package app.model.service;

import app.model.dao.UserInfo;
import app.model.vo.LoginVO;

import java.util.List;

public class UserInfoService {
    private UserInfo ui = new UserInfo();

    public List<LoginVO> getltv() {
        return ui.getUserInfo();
    }

    public void setltv(LoginVO ltv) {
        ui.setUserInfo(ltv);
    }
}

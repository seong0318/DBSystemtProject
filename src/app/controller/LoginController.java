package app.controller;

import app.Main;
import app.model.service.UserInfoService;
import app.model.vo.LoginVO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class LoginController {
    @FXML
    private Button check;
    @FXML
    private TextField id;
    @FXML
    private TextField pw;

    private Main mainApp;
    private UserInfoService uis;

    public LoginController() {
    }

    @FXML
    private void initialize() {
        uis = new UserInfoService();
        id.setPromptText("아이디를 입력해주세요.");
        id.setPromptText("비밀번호를 입력해주세요.");
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void LoginBtnClick(MouseEvent mouseEvent) {
        LoginVO ltv = new LoginVO();
        List<LoginVO> lltv = uis.getltv();

        if (pw.getText().length() > 0 && id.getText().length() > 0) {
            ltv.setId(id.getText());
            ltv.setPw(pw.getText());
            uis.setltv(ltv);
        }
    }
}

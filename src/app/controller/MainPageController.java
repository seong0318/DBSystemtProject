package app.controller;

import app.Main;
import app.controller.util.MovePage;
import app.model.service.UserInfoService;
import app.model.vo.LoginVO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class MainPageController {
    @FXML
    private Button studentlist;
    @FXML
    private Button sectionlist;

    private MovePage movePage = new MovePage();
    private Main mainApp;
    private UserInfoService uis;

    public MainPageController() {
    }

    @FXML
    private void initialize() {
        uis = new UserInfoService();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void studentListBtnClick(MouseEvent mouseEvent) {
        movePage.movePageBtnAction(studentlist, "/app/view/StudentList.fxml");
    }

    public void sectionListBtnClick(MouseEvent mouseEvent) {
        movePage.movePageBtnAction(sectionlist, "/app/view/SectionList.fxml");
    }
}

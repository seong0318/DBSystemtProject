package app.controller;

import app.Main;
import app.controller.util.MovePage;
import app.model.service.UserInfoService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


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

    public void sectionListBtnClick(MouseEvent mouseEvent) {
        movePage.movePageBtnAction(studentlist, "/app/view/SectionList.fxml");
    }

    public void studentListBtnClick(MouseEvent mouseEvent) {
        movePage.movePageBtnAction(sectionlist, "/app/view/StudentList.fxml");
    }
}

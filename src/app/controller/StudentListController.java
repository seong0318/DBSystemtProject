package app.controller;

import app.controller.util.MovePage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.event.MouseEvent;

public class StudentListController {
    @FXML
    private Button moveMainPageBtn;

    private MovePage movePage = new MovePage();

    public StudentListController() {
    }

    public void moveMainPageBtnClick(javafx.scene.input.MouseEvent mouseEvent) {
        movePage.movePageBtnAction(moveMainPageBtn, "/app/view/MainPage.fxml");
    }
}

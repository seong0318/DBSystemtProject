package app.controller;

import app.controller.util.MovePage;
import app.model.dao.StudentDAO;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CapstoneCheckController {
    @FXML
    private TextField studentIdText;
    @FXML
    private Button checkBtn;
    @FXML
    private Text resultText;
    @FXML
    private Button moveMainPageBtn;

    private MovePage movePage = new MovePage();

    public CapstoneCheckController() {
    }

    @FXML
    public void initialize() {

    }

    public void moveMainPageBtnClick(javafx.scene.input.MouseEvent mouseEvent) {
        movePage.movePageBtnAction(moveMainPageBtn, "/app/view/MainPage.fxml");
    }


    public void checkBtnClick(javafx.scene.input.MouseEvent mouseEvent) {
        StudentDAO studentDAO = new StudentDAO();
        String studentId = studentIdText.getText();
        if (studentId.trim().isEmpty()) {
            return;
        }

        int result = studentDAO.capstoneCheck(studentId);
        if (result == 1)
            resultText.setText("축하합니다! 캡스톤 면제입니다!");
        else
            resultText.setText("면제 대상이 아닙니다.");
    }
}

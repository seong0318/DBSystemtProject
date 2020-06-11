package app.controller;

import app.controller.util.MovePage;
import app.model.dao.StudentDAO;
import app.model.vo.StudentVO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class StudentListController {
    @FXML
    private Button moveMainPageBtn;

    private MovePage movePage = new MovePage();

    public StudentListController() {
        StudentDAO student = new StudentDAO();
        ArrayList<StudentVO> studentList = student.all();
    }

    public void moveMainPageBtnClick(javafx.scene.input.MouseEvent mouseEvent) {
        movePage.movePageBtnAction(moveMainPageBtn, "/app/view/MainPage.fxml");
    }
}

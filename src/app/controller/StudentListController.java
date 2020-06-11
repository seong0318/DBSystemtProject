package app.controller;

import app.controller.util.MovePage;
import app.model.dao.StudentDAO;
import app.model.vo.StudentVO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

class TableRowDataModel {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty dept;
    private IntegerProperty year;
    private IntegerProperty totCred;
    private IntegerProperty majorCred;
    private IntegerProperty liberalCred;
    private IntegerProperty engGrade;
    private IntegerProperty volunteerTime;
    private IntegerProperty capstone;

    TableRowDataModel(IntegerProperty id, StringProperty name, StringProperty dept, IntegerProperty year,
                      IntegerProperty totCred, IntegerProperty majorCred, IntegerProperty liberalCred,
                      IntegerProperty engGrade, IntegerProperty volunteerTime, IntegerProperty capstone) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.year = year;
        this.totCred = totCred;
        this.majorCred = majorCred;
        this.liberalCred = liberalCred;
        this.engGrade = engGrade;
        this.volunteerTime = volunteerTime;
        this.capstone = capstone;
    }

    IntegerProperty idProperty() {
        return id;
    }

    StringProperty nameProperty() {
        return name;
    }

    StringProperty deptProperty() {
        return dept;
    }

    IntegerProperty yearProperty() {
        return year;
    }

    IntegerProperty totCredProperty() {
        return totCred;
    }

    IntegerProperty majorCredProperty() {
        return majorCred;
    }

    IntegerProperty liberalCredProperty() {
        return liberalCred;
    }

    IntegerProperty engGradeProperty() {
        return engGrade;
    }

    IntegerProperty volunteerTimeProperty() {
        return volunteerTime;
    }

    IntegerProperty capstoneProperty() {
        return capstone;
    }
}

public class StudentListController {
    private int PAGE_NUM_ELEM = 100;

    @FXML
    private Button moveMainPageBtn;
    @FXML
    private TableView<TableRowDataModel> studentTable;
    @FXML
    private TableColumn<TableRowDataModel, Integer> idCol;
    @FXML
    private TableColumn<TableRowDataModel, String> nameCol;
    @FXML
    private TableColumn<TableRowDataModel, String> deptCol;
    @FXML
    private TableColumn<TableRowDataModel, Integer> yearCol;
    @FXML
    private TableColumn<TableRowDataModel, Integer> totCredCol;
    @FXML
    private TableColumn<TableRowDataModel, Integer> majorCredCol;
    @FXML
    private TableColumn<TableRowDataModel, Integer> liberalCredCol;
    @FXML
    private TableColumn<TableRowDataModel, Integer> engGradeCol;
    @FXML
    private TableColumn<TableRowDataModel, Integer> volunteerCol;
    @FXML
    private TableColumn<TableRowDataModel, Integer> capstoneCol;
    @FXML
    private Pagination pagination;
    @FXML
    private TextField selectPage;
    @FXML
    private Button movePageBtn;

    private ObservableList<TableRowDataModel> rowList = FXCollections.observableArrayList();
    private MovePage movePage = new MovePage();
    private int numStudent;

    public StudentListController() {
        StudentDAO student = new StudentDAO();
        ArrayList<StudentVO> studentList = student.getPage(0, PAGE_NUM_ELEM);
        addRowList(studentList);
        numStudent = student.count();
    }

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        deptCol.setCellValueFactory(cellData -> cellData.getValue().deptProperty());
        yearCol.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        totCredCol.setCellValueFactory(cellData -> cellData.getValue().totCredProperty().asObject());
        majorCredCol.setCellValueFactory(cellData -> cellData.getValue().majorCredProperty().asObject());
        liberalCredCol.setCellValueFactory(cellData -> cellData.getValue().liberalCredProperty().asObject());
        engGradeCol.setCellValueFactory(cellData -> cellData.getValue().engGradeProperty().asObject());
        volunteerCol.setCellValueFactory(cellData -> cellData.getValue().volunteerTimeProperty().asObject());
        capstoneCol.setCellValueFactory(cellData -> cellData.getValue().capstoneProperty().asObject());

        studentTable.setItems(rowList);

        ChangeListener<Number> paginationChangeListener = (observable, oldValue, newValue) -> changePage();
        pagination.setPageCount(numStudent / PAGE_NUM_ELEM);
        pagination.currentPageIndexProperty().addListener(paginationChangeListener);
    }

    private void changePage() {
        int currentPageIndex = pagination.getCurrentPageIndex();
        updateRowList(currentPageIndex);
    }

    public void moveMainPageBtnClick(javafx.scene.input.MouseEvent mouseEvent) {
        movePage.movePageBtnAction(moveMainPageBtn, "/app/view/MainPage.fxml");
    }

    public void movePageBtnClick(javafx.scene.input.MouseEvent mouseEvent) {
        int numPage = Integer.parseInt(selectPage.getText());
        updateRowList(numPage);
        pagination.setCurrentPageIndex(numPage - 1);
    }

    private void addRowList(ArrayList<StudentVO> studentList) {
        for (StudentVO elem : studentList) {
            TableRowDataModel row;
            IntegerProperty id = new SimpleIntegerProperty(elem.getStudentId());
            StringProperty name = new SimpleStringProperty(elem.getName());
            StringProperty dept = new SimpleStringProperty(elem.getDeptName());
            IntegerProperty year = new SimpleIntegerProperty(elem.getYear());
            IntegerProperty totCred = new SimpleIntegerProperty(elem.getTotCred());
            IntegerProperty majorCred = new SimpleIntegerProperty(elem.getMajorCred());
            IntegerProperty liberalCred = new SimpleIntegerProperty(elem.getLiberalArtsCred());
            IntegerProperty engGrade = new SimpleIntegerProperty(elem.getOfficialEngGrade());
            IntegerProperty volunteer = new SimpleIntegerProperty(elem.getVolunteerTime());
            IntegerProperty capstone = new SimpleIntegerProperty(elem.getCapstone());
            row = new TableRowDataModel(id, name, dept, year, totCred, majorCred, liberalCred, engGrade, volunteer, capstone);
            rowList.add(row);
        }
    }

    private void updateRowList(int pageNum) {
        StudentDAO student = new StudentDAO();
        ArrayList<StudentVO> studentList = student.getPage(pageNum * PAGE_NUM_ELEM, PAGE_NUM_ELEM);

        rowList.clear();
        addRowList(studentList);
    }


}

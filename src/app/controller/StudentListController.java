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
import javafx.util.Pair;

import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class StudentTableRowDataModel {
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

    StudentTableRowDataModel(IntegerProperty id, StringProperty name, StringProperty dept, IntegerProperty year,
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
    private TableView<StudentTableRowDataModel> studentTable;
    @FXML
    private TableColumn<StudentTableRowDataModel, Integer> idCol;
    @FXML
    private TableColumn<StudentTableRowDataModel, String> nameCol;
    @FXML
    private TableColumn<StudentTableRowDataModel, String> deptCol;
    @FXML
    private TableColumn<StudentTableRowDataModel, Integer> yearCol;
    @FXML
    private TableColumn<StudentTableRowDataModel, Integer> totCredCol;
    @FXML
    private TableColumn<StudentTableRowDataModel, Integer> majorCredCol;
    @FXML
    private TableColumn<StudentTableRowDataModel, Integer> liberalCredCol;
    @FXML
    private TableColumn<StudentTableRowDataModel, Integer> engGradeCol;
    @FXML
    private TableColumn<StudentTableRowDataModel, Integer> volunteerCol;
    @FXML
    private TableColumn<StudentTableRowDataModel, Integer> capstoneCol;
    @FXML
    private Pagination pagination;
    @FXML
    private TextField selectPage;
    @FXML
    private Button movePageBtn;
    @FXML
    private TextField idText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField deptText;
    @FXML
    private TextField yearText;
    @FXML
    private Button searchBtn;

    private ArrayList<StudentVO> studentList;
    private ObservableList<StudentTableRowDataModel> rowList = FXCollections.observableArrayList();
    private MovePage movePage = new MovePage();

    public StudentListController() {
        StudentDAO student = new StudentDAO();
        studentList = student.all();
        addRowList(studentList);
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
        pagination.setPageCount(studentList.size() / PAGE_NUM_ELEM);
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

    public void searchBtnClick(javafx.scene.input.MouseEvent mouseEvent) {
        StudentDAO student = new StudentDAO();
        String studentId = idText.getText();
        String name = nameText.getText();
        String dept = deptText.getText();
        String year = yearText.getText();
        List<String> condition1;
        List<String> condition2;
        List<String> condition3;
        List<String> condition4;

        if (studentId.trim().isEmpty()) condition1 = new ArrayList<>(Arrays.asList("student_id", "is not", "null"));
        else condition1 = new ArrayList<>(Arrays.asList("student_id", "=", studentId));

        if (name.trim().isEmpty()) condition2 = new ArrayList<>(Arrays.asList("name", "is not", "null"));
        else condition2 = new ArrayList<>(Arrays.asList("name", "like", "\"%" + name + "%\""));

        if (dept.trim().isEmpty()) condition3 = new ArrayList<>(Arrays.asList("dept_name", "is not", "null"));
        else condition3 = new ArrayList<>(Arrays.asList("dept_name", "like", "\"%" + dept + "%\""));

        if (year.trim().isEmpty()) condition4 = new ArrayList<>(Arrays.asList("year", "is not", "null"));
        else condition4 = new ArrayList<>(Arrays.asList("year", "=", year));

        List<List<String>> conditionList = new ArrayList<>();
        conditionList.add(condition1);
        conditionList.add(condition2);
        conditionList.add(condition3);
        conditionList.add(condition4);
        studentList = student.get(conditionList);   // studentList 교체

//        rowList.clear();
//        addRowList(studentList);
        rowList.clear();
        addRowList(studentList);
        pagination.setPageCount(studentList.size() / PAGE_NUM_ELEM);
    }

    private void addRowList(ArrayList<StudentVO> studentList) {
        for (StudentVO elem : studentList) {
            StudentTableRowDataModel row;
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
            row = new StudentTableRowDataModel(id, name, dept, year, totCred, majorCred, liberalCred, engGrade, volunteer, capstone);
            rowList.add(row);
        }
    }

    private void updateRowList(int pageNum) {
        ArrayList<StudentVO> studentListPage = paginationAction(pageNum);

        rowList.clear();
        addRowList(studentListPage);
    }

    private ArrayList<StudentVO> paginationAction(int page) {
        ArrayList<StudentVO> studentListPage = new ArrayList<>();
        int start = page * PAGE_NUM_ELEM;
        for (int i = 0; i < PAGE_NUM_ELEM; i++) {
            if (start + i >= studentList.size()) break;
            studentListPage.add(studentList.get(start + i));
        }
        return studentListPage;
    }
}

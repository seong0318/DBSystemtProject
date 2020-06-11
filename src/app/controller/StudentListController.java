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
    @FXML
    private TextField selectedIdText;
    @FXML
    private TextField selectedNameText;
    @FXML
    private TextField selectedDeptText;
    @FXML
    private TextField selectedYearText;
    @FXML
    private TextField selectedCredText;
    @FXML
    private TextField selectedMajorText;
    @FXML
    private TextField selectedLiberalText;
    @FXML
    private TextField selectedEngText;
    @FXML
    private TextField selectedVolunteerText;
    @FXML
    private TextField selectedCapstoneText;
    @FXML
    private Button editBtn;

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
        updateRowList(0);

        ChangeListener<Number> paginationChangeListener = (observable, oldValue, newValue) -> changePage();
        ChangeListener<StudentTableRowDataModel> tableSelectListener = (observable, oldValue, newValue) -> selectRow();
        pagination.setPageCount(studentList.size() / PAGE_NUM_ELEM + 1);
        pagination.currentPageIndexProperty().addListener(paginationChangeListener);
        studentTable.getSelectionModel().selectedItemProperty().addListener(tableSelectListener);
    }

    private void changePage() {
        int currentPageIndex = pagination.getCurrentPageIndex();
        updateRowList(currentPageIndex);
    }

    private void selectRow() {
        StudentTableRowDataModel model = studentTable.getSelectionModel().getSelectedItem();
        selectedIdText.setText(model.idProperty().getValue().toString());
        selectedNameText.setText(model.nameProperty().getValue());
        selectedDeptText.setText(model.deptProperty().getValue());
        selectedYearText.setText(model.yearProperty().getValue().toString());
        selectedCredText.setText(model.totCredProperty().getValue().toString());
        selectedMajorText.setText(model.majorCredProperty().getValue().toString());
        selectedLiberalText.setText(model.liberalCredProperty().getValue().toString());
        selectedEngText.setText(model.engGradeProperty().getValue().toString());
        selectedVolunteerText.setText(model.volunteerTimeProperty().getValue().toString());
        selectedCapstoneText.setText(model.capstoneProperty().getValue().toString());
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
        List<String> condition;
        List<List<String>> conditionList = new ArrayList<>();

        if (!studentId.trim().isEmpty()) {
            condition = new ArrayList<>(Arrays.asList("student_id", "=", studentId));
            conditionList.add(condition);
        }
        if (!name.trim().isEmpty()) {
            condition = new ArrayList<>(Arrays.asList("name", "like", "\"%" + name + "%\""));
            conditionList.add(condition);
        }
        if (!dept.trim().isEmpty()) {
            condition = new ArrayList<>(Arrays.asList("dept_name", "like", "\"%" + dept + "%\""));
            conditionList.add(condition);
        }
        if (!year.trim().isEmpty()) {
            condition = new ArrayList<>(Arrays.asList("year", "=", year));
            conditionList.add(condition);
        }

        studentList = student.get(conditionList);
        pagination.setPageCount(studentList.size() / PAGE_NUM_ELEM + 1);
        pagination.setCurrentPageIndex(0);
        updateRowList(0);
    }

    public void editBtnClick(javafx.scene.input.MouseEvent mouseEvent) {

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

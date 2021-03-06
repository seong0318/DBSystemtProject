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
    @FXML
    private ComboBox colCombobox;
    @FXML
    private TextField originValueText;
    @FXML
    private TextField newValueText;
    @FXML
    private Button allEditBtn;

    private ArrayList<StudentVO> studentList;
    private ObservableList<StudentTableRowDataModel> rowList = FXCollections.observableArrayList();
    private ObservableList<String> comboboxElem = FXCollections.observableArrayList("입학년도",
            "수강학점", "전공학점", "교양학점", "토익점수", "봉사시간", "캡스톤");
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

        colCombobox.setItems(comboboxElem);
    }

    private void changePage() {
        int currentPageIndex = pagination.getCurrentPageIndex();
        updateRowList(currentPageIndex);
    }

    private void selectRow() {
        StudentTableRowDataModel model = studentTable.getSelectionModel().getSelectedItem();
        if (model == null) return;
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
        StudentVO student = new StudentVO();
        StudentDAO studentDAO = new StudentDAO();
        int id = Integer.parseInt(selectedIdText.getText());
        String name = selectedNameText.getText();
        String dept = selectedDeptText.getText();
        int year = Integer.parseInt(selectedYearText.getText());
        int totCred = Integer.parseInt(selectedCredText.getText());
        int majorCred = Integer.parseInt(selectedMajorText.getText());
        int liberalCred = Integer.parseInt(selectedLiberalText.getText());
        int eng = Integer.parseInt(selectedEngText.getText());
        int volunteer = Integer.parseInt(selectedVolunteerText.getText());
        int capstone = Integer.parseInt(selectedCapstoneText.getText());

        student.setStudentId(id);
        student.setName(name);
        student.setYear(year);
        student.setDeptName(dept);
        student.setTotCred(totCred);
        student.setMajorCred(majorCred);
        student.setLiberalArtsCred(liberalCred);
        student.setOfficialEngGrade(eng);
        student.setVolunteerTime(volunteer);
        student.setCapstone(capstone);

        studentDAO.update(student);
    }

    public void allEditBtnClick() {
        String dbCol;
        StudentDAO studentDAO = new StudentDAO();
        String selectedCol = colCombobox.getValue().toString();
        int findVal = Integer.parseInt(originValueText.getText());
        int newVal = Integer.parseInt(newValueText.getText());

        switch (selectedCol) {
            case "입학년도":
                dbCol = "year";
                break;
            case "수강학점":
                dbCol = "tot_cred";
                break;
            case "전공학점":
                dbCol = "major_cred";
                break;
            case "교양학점":
                dbCol = "liberal_arts_cred";
                break;
            case "토익점수":
                dbCol = "official_eng_grade";
                break;
            case "봉사시간":
                dbCol = "volunteer_time";
                break;
            case "캡스톤":
                dbCol = "capstone";
                break;
            default:
                return;
        }
        int cnt = studentDAO.matchUpdate(dbCol, findVal, newVal);
        System.out.println(cnt);
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

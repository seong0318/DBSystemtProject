package app.controller;

import app.controller.util.MovePage;
import app.model.dao.SectionDAO;
import app.model.vo.SectionVO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SecTableRowDataModel {
    private StringProperty dept;
    private StringProperty title;
    private IntegerProperty major;
    private IntegerProperty cred;
    private IntegerProperty year;
    private StringProperty semester;
    private IntegerProperty day;
    private StringProperty start;
    private StringProperty end;
    private StringProperty location;
    private IntegerProperty sectionId;
    private IntegerProperty timeslotId;
    private IntegerProperty courseId;

    SecTableRowDataModel(StringProperty dept, StringProperty title, IntegerProperty major, IntegerProperty cred,
                         IntegerProperty year, StringProperty semester, IntegerProperty day, StringProperty start,
                         StringProperty end, StringProperty location, IntegerProperty sectionId,
                         IntegerProperty timeslotId, IntegerProperty courseId) {
        this.dept = dept;
        this.title = title;
        this.major = major;
        this.cred = cred;
        this.year = year;
        this.semester = semester;
        this.day = day;
        this.start = start;
        this.end = end;
        this.location = location;
        this.sectionId = sectionId;
        this.timeslotId = timeslotId;
        this.courseId = courseId;
    }

    StringProperty deptProperty() {
        return dept;
    }

    StringProperty titleProperty() {
        return title;
    }

    IntegerProperty majorProperty() {
        return major;
    }

    IntegerProperty credProperty() {
        return cred;
    }

    IntegerProperty yearProperty() {
        return year;
    }

    StringProperty semesterProperty() {
        return semester;
    }

    IntegerProperty dayProperty() {
        return day;
    }

    StringProperty startProperty() {
        return start;
    }

    StringProperty endProperty() {
        return end;
    }

    StringProperty locationProperty() {
        return location;
    }

    IntegerProperty sectionIdProperty() {
        return sectionId;
    }

    IntegerProperty timeslotIdProperty() {
        return timeslotId;
    }

    IntegerProperty courseIdProperty() {
        return courseId;
    }
}

public class SectionListController {
    private int PAGE_NUM_ELEM = 100;

    @FXML
    private Button moveMainPageBtn;
    @FXML
    private TableView<SecTableRowDataModel> sectionTable;
    @FXML
    private TableColumn<SecTableRowDataModel, String> deptCol;
    @FXML
    private TableColumn<SecTableRowDataModel, String> titleCol;
    @FXML
    private TableColumn<SecTableRowDataModel, Integer> majorCol;
    @FXML
    private TableColumn<SecTableRowDataModel, Integer> credCol;
    @FXML
    private TableColumn<SecTableRowDataModel, Integer> yearCol;
    @FXML
    private TableColumn<SecTableRowDataModel, String> semesterCol;
    @FXML
    private TableColumn<SecTableRowDataModel, Integer> dayCol;
    @FXML
    private TableColumn<SecTableRowDataModel, String> startCol;
    @FXML
    private TableColumn<SecTableRowDataModel, String> endCol;
    @FXML
    private TableColumn<SecTableRowDataModel, String> locationCol;
    @FXML
    private TableColumn<SecTableRowDataModel, Integer> sectionIdCol;
    @FXML
    private TableColumn<SecTableRowDataModel, Integer> timeslotIdCol;
    @FXML
    private TableColumn<SecTableRowDataModel, Integer> courseIdCol;
    @FXML
    private Pagination pagination;
    @FXML
    private TextField selectPage;
    @FXML
    private TextField deptText;
    @FXML
    private TextField titleText;
    @FXML
    private TextField yearText;
    @FXML
    private TextField credText;
    @FXML
    private CheckBox springCheck;
    @FXML
    private CheckBox autumnCheck;
    @FXML
    private Button searchBtn;
    @FXML
    private TextField selectedDeptText;
    @FXML
    private TextField selectedTitleText;
    @FXML
    private TextField selectedMajorText;
    @FXML
    private TextField selectedCredText;
    @FXML
    private TextField selectedYearText;
    @FXML
    private TextField selectedSemesterText;
    @FXML
    private TextField selectedDayText;
    @FXML
    private TextField selectedStartText;
    @FXML
    private TextField selectedEndText;
    @FXML
    private TextField selectedClassText;
    @FXML
    private TextField selectedSectionIdText;
    @FXML
    private TextField selectedTimeslotIdText;
    @FXML
    private TextField selectedCourseIdText;
    @FXML
    private ComboBox colCombobox;
    @FXML
    private TextField originValueText;
    @FXML
    private TextField newValueText;
    @FXML
    private Button allEditBtn;

    private ArrayList<SectionVO> sectionList;
    private ObservableList<SecTableRowDataModel> rowList = FXCollections.observableArrayList();
    private ObservableList<String> comboboxElem = FXCollections.observableArrayList("강의명", "학점");
    private MovePage movePage = new MovePage();

    public SectionListController() {
        SectionDAO section = new SectionDAO();
        sectionList = section.all();
        addRowList(sectionList);
    }

    @FXML
    public void initialize() {
        deptCol.setCellValueFactory(cellData -> cellData.getValue().deptProperty());
        titleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        majorCol.setCellValueFactory(cellData -> cellData.getValue().majorProperty().asObject());
        credCol.setCellValueFactory(cellData -> cellData.getValue().credProperty().asObject());
        yearCol.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        semesterCol.setCellValueFactory(cellData -> cellData.getValue().semesterProperty());
        dayCol.setCellValueFactory(cellData -> cellData.getValue().dayProperty().asObject());
        startCol.setCellValueFactory(cellData -> cellData.getValue().startProperty());
        endCol.setCellValueFactory(cellData -> cellData.getValue().endProperty());
        locationCol.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        sectionIdCol.setCellValueFactory(cellData -> cellData.getValue().sectionIdProperty().asObject());
        timeslotIdCol.setCellValueFactory(cellData -> cellData.getValue().timeslotIdProperty().asObject());
        courseIdCol.setCellValueFactory(cellData -> cellData.getValue().courseIdProperty().asObject());

        sectionTable.setItems(rowList);
        updateRowList(0);

        ChangeListener<Number> paginationChangeListener = (observable, oldValue, newValue) -> changePage();
        ChangeListener<SecTableRowDataModel> tableSelectListener = (observable, oldValue, newValue) -> selectRow();
        pagination.setPageCount(sectionList.size() / PAGE_NUM_ELEM + 1);
        pagination.currentPageIndexProperty().addListener(paginationChangeListener);
        sectionTable.getSelectionModel().selectedItemProperty().addListener(tableSelectListener);

        colCombobox.setItems(comboboxElem);
    }

    private void changePage() {
        int currentPageIndex = pagination.getCurrentPageIndex();
        updateRowList(currentPageIndex);
    }

    private void selectRow() {
        SecTableRowDataModel model = sectionTable.getSelectionModel().getSelectedItem();
        if (model == null) return;
        selectedDeptText.setText(model.deptProperty().getValue());
        selectedTitleText.setText(model.titleProperty().getValue());
        selectedMajorText.setText(model.majorProperty().getValue().toString());
        selectedCredText.setText(model.credProperty().getValue().toString());
        selectedYearText.setText(model.yearProperty().getValue().toString());
        selectedSemesterText.setText(model.semesterProperty().get());
        selectedDayText.setText(model.dayProperty().getValue().toString());
        selectedStartText.setText(model.startProperty().getValue());
        selectedEndText.setText(model.endProperty().getValue());
        selectedClassText.setText(model.locationProperty().getValue());
        selectedSectionIdText.setText(model.sectionIdProperty().getValue().toString());
        selectedTimeslotIdText.setText(model.timeslotIdProperty().getValue().toString());
        selectedCourseIdText.setText(model.courseIdProperty().getValue().toString());
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
        SectionDAO section = new SectionDAO();
        String dept = deptText.getText();
        String title = titleText.getText();
        String year = yearText.getText();
        String cred = credText.getText();
        List<String> condition;
        List<List<String>> conditionList = new ArrayList<>();

        if (!dept.trim().isEmpty()) {
            condition = new ArrayList<>(Arrays.asList("dept_name", "like", "\"%" + dept + "%\""));
            conditionList.add(condition);
        }
        if (!title.trim().isEmpty()) {
            condition = new ArrayList<>(Arrays.asList("title", "like", "\"%" + title + "%\""));
            conditionList.add(condition);
        }
        if (!year.trim().isEmpty()) {
            condition = new ArrayList<>(Arrays.asList("year", "=", year));
            conditionList.add(condition);
        }
        if (!cred.trim().isEmpty()) {
            condition = new ArrayList<>(Arrays.asList("credit", "=", cred));
            conditionList.add(condition);
        }
        if (springCheck.isSelected()) {
            condition = new ArrayList<>(Arrays.asList("semester", "=", "\"spring\""));
            conditionList.add(condition);
        }
        if (autumnCheck.isSelected()) {
            condition = new ArrayList<>(Arrays.asList("semester", "=", "\"autumn\""));
            conditionList.add(condition);
        }

        sectionList = section.get(conditionList);
        pagination.setPageCount(sectionList.size() / PAGE_NUM_ELEM + 1);
        pagination.setCurrentPageIndex(0);
        updateRowList(0);
    }

    public void springCheckClick(javafx.scene.input.MouseEvent mouseEvent) {
        autumnCheck.setSelected(false);
    }

    public void autumnCheckClick(javafx.scene.input.MouseEvent mouseEvent) {
        springCheck.setSelected(false);
    }

    public void editBtnClick(javafx.scene.input.MouseEvent mouseEvent) {
        SectionVO section = new SectionVO();
        SectionDAO sectionDAO = new SectionDAO();
        String dept = selectedDeptText.getText();
        String title = selectedTitleText.getText();
        int major = Integer.parseInt(selectedMajorText.getText());
        int cred = Integer.parseInt(selectedCredText.getText());
        int year = Integer.parseInt(selectedYearText.getText());
        String semester = selectedSemesterText.getText();
        int day = Integer.parseInt(selectedDayText.getText());
        String start = selectedStartText.getText();
        String end = selectedEndText.getText();
        String classroom = selectedClassText.getText();
        String[] mobstr = classroom.split("-");
        String building = mobstr[0];
        String room = mobstr[1];
        int sectionId = Integer.parseInt(selectedSectionIdText.getText());
        int timeslotId = Integer.parseInt(selectedTimeslotIdText.getText());
        int courseId = Integer.parseInt(selectedCourseIdText.getText());

        section.setDeptName(dept);
        section.setTitle(title);
        section.setType(major);
        section.setCred(cred);
        section.setYear(year);
        section.setSemester(semester);
        section.setDay(day);
        section.setStartTime(start);
        section.setEndTime(end);
        section.setBuilding(building);
        section.setRoomNum(room);
        section.setSecId(sectionId);
        section.setTimeslotId(timeslotId);
        section.setCourseId(courseId);

        System.out.println(sectionDAO.update(section));
    }

    public void allEditBtnClick(javafx.scene.input.MouseEvent mouseEvent) {
        String dbCol;
        SectionDAO sectionDAO = new SectionDAO();
        String selectedCol = colCombobox.getValue().toString();
        String findVal = originValueText.getText();
        String newVal = newValueText.getText();

        switch (selectedCol) {
            case "강의명":
                dbCol = "title";
                break;
            case "학점":
                dbCol = "credit";
                break;
            default:
                return;
        }
        int cnt = sectionDAO.matchUpdate(dbCol, findVal, newVal);
        System.out.println(cnt);
    }

    private void addRowList(ArrayList<SectionVO> sectionList) {
        for (SectionVO elem : sectionList) {
            SecTableRowDataModel row;
            StringProperty dept = new SimpleStringProperty(elem.getDeptName());
            StringProperty title = new SimpleStringProperty(elem.getTitle());
            IntegerProperty major = new SimpleIntegerProperty(elem.getType());
            IntegerProperty cred = new SimpleIntegerProperty(elem.getCred());
            IntegerProperty year = new SimpleIntegerProperty(elem.getYear());
            StringProperty semester = new SimpleStringProperty((elem.getSemester()));
            IntegerProperty day = new SimpleIntegerProperty(elem.getDay());
            StringProperty start = new SimpleStringProperty(elem.getStartTime());
            StringProperty end = new SimpleStringProperty(elem.getEndTime());
            StringProperty location = new SimpleStringProperty(String.format("%s-%s", elem.getBuilding(),
                    elem.getRoomNum()));
            IntegerProperty sectionId = new SimpleIntegerProperty(elem.getSecId());
            IntegerProperty timeslotId = new SimpleIntegerProperty(elem.getTimeslotId());
            IntegerProperty courseId = new SimpleIntegerProperty(elem.getCourseId());
            row = new SecTableRowDataModel(dept, title, major, cred, year, semester, day, start, end, location,
                    sectionId, timeslotId, courseId);
            rowList.add(row);
        }
    }

    private void updateRowList(int pageNum) {
        ArrayList<SectionVO> sectionListPage = paginationAction(pageNum);

        rowList.clear();
        addRowList(sectionListPage);
    }

    private ArrayList<SectionVO> paginationAction(int page) {
        ArrayList<SectionVO> sectionListPage = new ArrayList<>();
        int start = page * PAGE_NUM_ELEM;
        for (int i = 0; i < PAGE_NUM_ELEM; i++) {
            if (start + i >= sectionList.size()) break;
            sectionListPage.add(sectionList.get(start + i));
        }
        return sectionListPage;
    }
}

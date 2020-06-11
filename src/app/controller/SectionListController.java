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

class SectionTableRowDataModel {
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

    SectionTableRowDataModel(StringProperty dept, StringProperty title, IntegerProperty major, IntegerProperty cred,
                             IntegerProperty year, StringProperty semester, IntegerProperty day, StringProperty start,
                             StringProperty end, StringProperty location) {
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
    }

    public StringProperty deptProperty() {
        return dept;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public IntegerProperty majorProperty() {
        return major;
    }

    public IntegerProperty credProperty() {
        return cred;
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public StringProperty semesterProperty() {
        return semester;
    }

    public IntegerProperty dayProperty() {
        return day;
    }

    public StringProperty startProperty() {
        return start;
    }

    public StringProperty endProperty() {
        return end;
    }

    public StringProperty locationProperty() {
        return location;
    }
}

public class SectionListController {
    private int PAGE_NUM_ELEM = 100;

    @FXML
    private Button moveMainPageBtn;
    @FXML
    private TableView<SectionTableRowDataModel> sectionTable;
    @FXML
    private TableColumn<SectionTableRowDataModel, String> deptCol;
    @FXML
    private TableColumn<SectionTableRowDataModel, String> titleCol;
    @FXML
    private TableColumn<SectionTableRowDataModel, Integer> majorCol;
    @FXML
    private TableColumn<SectionTableRowDataModel, Integer> credCol;
    @FXML
    private TableColumn<SectionTableRowDataModel, Integer> yearCol;
    @FXML
    private TableColumn<SectionTableRowDataModel, String> semesterCol;
    @FXML
    private TableColumn<SectionTableRowDataModel, Integer> dayCol;
    @FXML
    private TableColumn<SectionTableRowDataModel, String> startCol;
    @FXML
    private TableColumn<SectionTableRowDataModel, String> endCol;
    @FXML
    private TableColumn<SectionTableRowDataModel, String> locationCol;
    @FXML
    private Pagination pagination;
    @FXML
    private TextField selectPage;
    @FXML
    private TextField deptText;
    @FXML
    private TextField nameText;
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

    private ArrayList<SectionVO> sectionList;
    private ObservableList<SectionTableRowDataModel> rowList = FXCollections.observableArrayList();
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

        sectionTable.setItems(rowList);

        ChangeListener<Number> paginationChangeListener = (observable, oldValue, newValue) -> changePage();
        pagination.setPageCount(sectionList.size() / PAGE_NUM_ELEM);
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

    }

    public void springCheckClick(javafx.scene.input.MouseEvent mouseEvent) {
        autumnCheck.setIndeterminate(false);
    }

    public void autumnCheckClick(javafx.scene.input.MouseEvent mouseEvent) {
        springCheck.setIndeterminate(false);
    }

    private void addRowList(ArrayList<SectionVO> sectionList) {
        for (SectionVO elem : sectionList) {
            SectionTableRowDataModel row;
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
            row = new SectionTableRowDataModel(dept, title, major, cred, year, semester, day, start, end, location);
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

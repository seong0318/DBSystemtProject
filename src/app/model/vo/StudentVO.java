package app.model.vo;

public class StudentVO {
    private int studentId;
    private String name;
    private String deptName;
    private int year;
    private int totCred;
    private int majorCred;
    private int liberalArtsCred;
    private int officialEngGrade;
    private int volunteerTime;
    private int capstone;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTotCred() {
        return totCred;
    }

    public void setTotCred(int totCred) {
        this.totCred = totCred;
    }

    public int getMajorCred() {
        return majorCred;
    }

    public void setMajorCred(int majorCred) {
        this.majorCred = majorCred;
    }

    public int getLiberalArtsCred() {
        return liberalArtsCred;
    }

    public void setLiberalArtsCred(int liberalArtsCred) {
        this.liberalArtsCred = liberalArtsCred;
    }

    public int getCapstone() {
        return capstone;
    }

    public void setCapstone(int capstone) {
        this.capstone = capstone;
    }

    public int getVolunteerTime() {
        return volunteerTime;
    }

    public void setVolunteerTime(int volunteerTime) {
        this.volunteerTime = volunteerTime;
    }

    public int getOfficialEngGrade() {
        return officialEngGrade;
    }

    public void setOfficialEngGrade(int officialEngGrade) {
        this.officialEngGrade = officialEngGrade;
    }
}

package app.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import app.model.vo.StudentVO;
import javafx.util.Pair;

public class StudentDAO extends ConnectDB {
    private Statement stmt;
    private ResultSet rs;
    private PreparedStatement pstmt;

    private StudentVO setStudentVO(ResultSet rs) throws SQLException {
        StudentVO student = new StudentVO();
        student.setStudentId(rs.getInt("student_id"));
        student.setName(rs.getString("name"));
        student.setDeptName(rs.getString("dept_name"));
        student.setYear(rs.getInt("year"));
        student.setTotCred(rs.getInt("tot_cred"));
        student.setMajorCred(rs.getInt("major_cred"));
        student.setLiberalArtsCred(rs.getInt("liberal_arts_cred"));
        student.setOfficialEngGrade(rs.getInt("official_eng_grade"));
        student.setVolunteerTime(rs.getInt("volunteer_time"));
        student.setCapstone(rs.getInt("capstone"));

        return student;
    }

    public ArrayList<StudentVO> all() {
        ArrayList<StudentVO> list = new ArrayList<StudentVO>();
        Connection conn = null;
        String sql = "SELECT * FROM student";

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                StudentVO student = setStudentVO(rs);
                list.add((student));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt, rs);
        }
        return null;
    }

    public ArrayList<StudentVO> getPage(int start, int end) {
        ArrayList<StudentVO> list = new ArrayList<StudentVO>();
        Connection conn = null;

        try {
            conn = getConnection();
            String sql = "SELECT * FROM student LIMIT ?, ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, start);
            pstmt.setInt(2, end);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                StudentVO student = setStudentVO(rs);
                list.add((student));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt, rs);
        }
        return null;
    }

    public boolean insert(StudentVO vo) {
        boolean result = false;
        Connection conn = null;

        try {
            conn = getConnection();

            // Column
            // PK , name, dept_name, year, tot_cred, major_cred, liberal_arts_cred, official_eng_grade, voluteer_time, capstone
            String sql = "INSERT INTO student VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, vo.getName());
            pstmt.setString(2, vo.getDeptName());
            pstmt.setInt(3, vo.getYear());
            pstmt.setInt(4, vo.getTotCred());
            pstmt.setInt(5, vo.getMajorCred());
            pstmt.setInt(6, vo.getLiberalArtsCred());
            pstmt.setInt(7, vo.getOfficialEngGrade());
            pstmt.setInt(8, vo.getVolunteerTime());
            pstmt.setInt(9, vo.getCapstone());
            int count = pstmt.executeUpdate();

            result = (count == 1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public int count() {
        int cnt = -1;
        Connection conn = null;
        String sql = "SELECT COUNT(*) FROM student";

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                cnt = rs.getInt(1);
            }
            return cnt;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt, rs);
        }
        return cnt;
    }

    public ArrayList<StudentVO> get(List<List<String>> condition) {
        ArrayList<StudentVO> list = new ArrayList<StudentVO>();
        Connection conn = null;
        String sql;
        StringBuilder sb = new StringBuilder("SELECT * FROM student WHERE ");

        for (List<String> elem : condition) {
            for (String el : elem) {
                sb.append(el).append(" ");
            }
            sb.append("AND ");
        }
        sql = sb.substring(0, sb.length() - 5);
        System.out.println(sql);

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                StudentVO student = setStudentVO(rs);
                list.add((student));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt, rs);
        }
        return null;
    }
}
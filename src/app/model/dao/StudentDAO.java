package app.model.dao;

import java.sql.*;
import java.util.ArrayList;

import app.model.SecretInfo;
import app.model.vo.StudentVO;

public class StudentDAO {
    private String dbuser = SecretInfo.username;
    private String dbpw = SecretInfo.password;

    private Statement stmt;
    private ResultSet rs;

    private Connection getConnection() throws SQLException {
        Connection conn = null;

        try {
//            Class.forName("com.mysql.jdbc.Driver");
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost/db_system_project?serverTimezone=UTC&useSSL=false";
            Class.forName(driver);
            conn = DriverManager.getConnection(url, dbuser, dbpw);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" 드라이버 로딩 실패 ");
        }

        return conn;
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ignored) {
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ignored) {
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public ArrayList<StudentVO> all() {
        ArrayList<StudentVO> list = new ArrayList<StudentVO>();
        Connection conn = null;
        String sql = "select * from student";

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
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
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            // Column
            // PK , name, dept_name, year, tot_cred, major_cred, liberal_arts_cred, official_eng_grade, voluteer_time, capstone
            String sql = "INSERT INTO user VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
}

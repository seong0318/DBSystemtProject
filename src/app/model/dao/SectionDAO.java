package app.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import app.model.vo.SectionVO;
import app.model.vo.StudentVO;

public class SectionDAO extends ConnectDB {
    private Statement stmt;
    private ResultSet rs;
    private PreparedStatement pstmt;

    private SectionVO setSectionVO(ResultSet rs) throws SQLException {
        int type = rs.getInt("type");
        SectionVO section = new SectionVO();
        section.setCourseId(rs.getInt("course_id"));
        section.setTimeslotId(rs.getInt("timeslot_id"));
        section.setSecId(rs.getInt("sec_id"));
        section.setSemester(rs.getString("semester"));
        section.setYear(rs.getInt("year"));
        section.setBuilding(rs.getString("building"));
        section.setRoomNum(rs.getString("room_number"));
        section.setDay(rs.getInt("day"));
        section.setStartTime(rs.getTime("start_time").toString());
        section.setEndTime(rs.getTime("end_time").toString());
        section.setTitle(rs.getString("title"));
        section.setDeptName(rs.getString("dept_name"));
        section.setCred(rs.getInt("credit"));

        return section;
    }

    public ArrayList<SectionVO> all() {
        ArrayList<SectionVO> list = new ArrayList<SectionVO>();
        Connection conn = null;
        String sql = "select * from section natural join timeslot natural join course";

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                SectionVO section = setSectionVO(rs);
                list.add((section));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt, rs);
        }
        return null;
    }

    public int update(SectionVO section) {
        Connection conn = null;
        int result = 0;
        String S3_1 = "UPDATE section SET building = ?, room_number = ? WHERE course_id = ? and" +
                " sec_id = ? and semester = ? and year = ?";
        String S3_2 = "UPDATE course SET title = ?, credit = ?, type = ? WHERE course_id = ?";
        try {
            conn = getConnection();
            conn.setAutoCommit(false);                  // T3
            pstmt = conn.prepareStatement(S3_1);
            pstmt.setString(1, section.getBuilding());
            pstmt.setString(2, section.getRoomNum());
            pstmt.setInt(3, section.getCourseId());
            pstmt.setInt(4, section.getSecId());
            pstmt.setString(5, section.getSemester());
            pstmt.setInt(6, section.getYear());
            System.out.println(pstmt.toString());
            result += pstmt.executeUpdate();

            pstmt = conn.prepareStatement(S3_2);
            pstmt.setString(1, section.getTitle());
            pstmt.setInt(2, section.getCred());
            pstmt.setInt(3, section.getType());
            pstmt.setInt(4, section.getCourseId());
            System.out.println(pstmt.toString());
            result += pstmt.executeUpdate();
            conn.commit();                              // T3
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt, rs);
        }
        return result;
    }

    public ArrayList<SectionVO> get(List<List<String>> condition) {
        ArrayList<SectionVO> list = new ArrayList<>();
        Connection conn = null;
        String sql;
        StringBuilder sb = new StringBuilder("SELECT * FROM section NATURAL JOIN timeslot NATURAL JOIN course WHERE ");

        for (List<String> elem : condition) {
            for (String el : elem) {
                sb.append(el).append(" ");
            }
            sb.append("AND ");
        }
        sql = sb.substring(0, sb.length() - 5);

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                SectionVO section = setSectionVO(rs);
                list.add(section);
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
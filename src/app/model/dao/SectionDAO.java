package app.model.dao;

import java.sql.*;
import java.util.ArrayList;

import app.model.vo.SectionVO;
import app.model.vo.StudentVO;

public class SectionDAO extends ConnectDB {
    private Statement stmt;
    private ResultSet rs;
    private PreparedStatement pstmt;

    private SectionVO setSectionVO(ResultSet rs) throws SQLException {
        SectionVO section = new SectionVO();
        section.setCourseId(rs.getInt("course_id"));
        section.setTimeslotId(rs.getInt("timeslot_id"));
        section.setSecId(rs.getInt("sec_id"));
        section.setSemester(rs.getString("semester"));
        section.setYear(rs.getInt("year"));
        section.setBuilding(rs.getString("building"));
        section.setRoomNum(rs.getString("room_number"));
        section.setDay(rs.getInt("day"));
        section.setStartTime(rs.getTime("start_time"));
        section.setEndTime(rs.getTime("end_time"));
        section.setTitle(rs.getString("title"));
        section.setDeptName(rs.getString("dept_name"));
        section.setCred(rs.getInt("credit"));
        section.setType(rs.getInt("type"));

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

    public ArrayList<SectionVO> getPage(int start, int end) {
        ArrayList<SectionVO> list = new ArrayList<SectionVO>();
        Connection conn = null;

        try {
            conn = getConnection();
            String sql = "select * from section natural join timeslot natural join course limit ?, ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, start);
            pstmt.setInt(2, end);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                SectionVO section = new SectionVO();
                section = setSectionVO(rs);
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
}
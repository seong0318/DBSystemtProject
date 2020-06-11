package app.model.dao;

import app.model.SecretInfo;

import java.sql.*;

abstract class ConnectDB {
    private String dbuser = SecretInfo.username;
    private String dbpw = SecretInfo.password;

    Connection getConnection() throws SQLException {
        Connection conn = null;

        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost/db_system_project?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
            Class.forName(driver);
            conn = DriverManager.getConnection(url, dbuser, dbpw);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" 드라이버 로딩 실패 ");
        }

        return conn;
    }

    void close(Connection con, Statement stmt, ResultSet rs) {
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
}

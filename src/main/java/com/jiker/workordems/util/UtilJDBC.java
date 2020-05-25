package com.jiker.workordems.util;

import java.sql.*;

public class UtilJDBC {

    private static String driver="com.mysql.jdbc.Driver";
    //private static String url="jdbc:mysql://127.0.0.1:3306/workorderms";
    private static String url="jdbc:mysql://127.0.0.1:3306/workorderms?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static String user="root";
    private static String password="root";
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 Connetion
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    /**
     * 关闭ResultSet资源
     * @param rs
     */
    public static void close(ResultSet rs){
        if(null != rs){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭Statement资源
     * @param stmt
     */
    public static void close(Statement stmt){
        if(null != stmt){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭PreparedStatement资源
     * @param psmt
     */
    public static void close(PreparedStatement psmt){
        if(null != psmt){
            try {
                psmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭close资源
     * @param conn
     */
    public static void close(Connection conn){
        if(null != conn){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 关闭资源
     * @param rs
     * @param stmt
     * @param conn
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn){
        close(rs);
        close(stmt);
        close(conn);
    }
    /**
     * 关闭资源
     * @param rs
     * @param stmt
     * @param conn
     * @param psmt
     */
    public static void close(ResultSet rs, Statement stmt, PreparedStatement psmt, Connection conn){
        close(rs);
        close(stmt);
        close(conn);
        close(psmt);

    }

}

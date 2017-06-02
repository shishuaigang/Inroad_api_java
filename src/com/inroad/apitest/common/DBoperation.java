package com.inroad.apitest.common;

import java.sql.*;

/**
 * 连接数据库，实现增删操作
 */
public class DBoperation {

    private static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String dbURL = "jdbc:sqlserver://192.168.31.99\\sql2012;DatabaseName=Inroad_Test_Result";
    private static String userName = "sgshi";
    private static String userPwd = "ssg12345!";

    //连接数据库
    private static Connection getCoonection() {
        Connection conn = null;
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(dbURL, userName, userPwd);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("-------连接数据库失败--------");
        }
        return conn;
    }

    // select语句使用这个方法
    public static ResultSet executeQuery(String SQL) {
        Connection conn = getCoonection();
        ResultSet rs = null;
        try {
            if (conn != null) {
                System.out.println("-------连接数据库成功--------");
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(SQL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //insert, delete, update语句都使用这个方法
    public static boolean executeUpdate(String SQL) {
        Connection conn = getCoonection();
        boolean t = false;
        try {
            if (conn != null) {
                System.out.println("-------连接数据库成功--------");
                Statement stmt = null;
                stmt = conn.createStatement();
                int result = 0;
                if (stmt != null) {
                    result = stmt.executeUpdate(SQL);
                    if (result > 0) {
                        t = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // 不做任何处理，静默处理
                }
        }
        return t;
    }


    public static void main(String args[]) {
        DBoperation dbo = new DBoperation();
        String SQL = "select * from Inroad_Crawler_Test where TestNo=201705171656";
        if (executeQuery(SQL) != null) {
            System.out.println("查询成功");
        } else {
            System.out.println("查询失败");
        }
    }
}

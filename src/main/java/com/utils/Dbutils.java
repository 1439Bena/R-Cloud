package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class Dbutils {

    private static String dbDriver;
    private static String dbUrl;
    private static String dbUser;
    private static String dbPwd;
    // 三大对象
    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    static {
        InputStream in = Dbutils.class.getResourceAsStream("/jdbc.properties");

        Properties pro = new Properties();

        try {
            pro.load(in);

            dbDriver = pro.getProperty("jdbc.dbDriver");
            dbUrl = pro.getProperty("jdbc.dbUrl");
            dbUser = pro.getProperty("jdbc.dbUser");
            dbPwd = pro.getProperty("jdbc.dbPwd");

            in.close();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    // 加载驱动
    static {
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     *
     * @return 返回连接对象
     */
    private Connection getConnection() {
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 增删改的通用方法
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public int executeUpdate(String sql, Object... params) throws SQLException {
        conn = getConnection(); // 获取连接
        psmt = conn.prepareStatement(sql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                psmt.setObject(i + 1, params[i]); // 设置参数值
            }
        }
        int rows = psmt.executeUpdate(); // 执行sql
        // 关闭对象
        closeAll();
        return rows;
    }

    public int executeUpdate(StringBuilder sql, List<Object> params) throws SQLException {
        conn = getConnection(); // 获取连接
        psmt = conn.prepareStatement(sql.toString());
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                psmt.setObject(i + 1, params.get(i)); // 设置参数值
            }
        }
        int rows = psmt.executeUpdate(); // 执行sql
        // 关闭对象
        closeAll();
        return rows;
    }

    /**
     * 查询的通用方法
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery(String sql, Object... params) throws SQLException {
        conn = getConnection(); // 获取连接
        psmt = conn.prepareStatement(sql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                psmt.setObject(i + 1, params[i]); // 设置参数值
            }
        }
        rs = psmt.executeQuery(); // 执行查询
        return rs;
    }

    public ResultSet executeQuery(StringBuilder sql, List<Object> params) throws SQLException {
        conn = getConnection(); // 获取连接
        psmt = conn.prepareStatement(sql.toString());
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                psmt.setObject(i + 1, params.get(i)); // 设置参数值
            }
        }
        rs = psmt.executeQuery(); // 执行查询
        return rs;
    }
    /**
     * 关闭对象的通用方法
     */
    public void closeAll() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (psmt != null) {
                psmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection conn = new Dbutils().getConnection();
        System.out.println(conn);
    }

}

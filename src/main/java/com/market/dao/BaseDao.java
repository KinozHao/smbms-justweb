package com.market.dao;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author kinoz
 * @Date 2022/7/8 - 10:56
 * @apiNote 数据库操纵的公共类
 */
public class BaseDao {
    private static final String driver;
    private static final String url;
    private static final String username;
    private static final String password;
    static {
        Properties properties = new Properties();
        InputStream ins = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(ins);
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");

    }
    //1.获取数据库连接
    public static Connection getConnection(){
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    //2.DQL
    //params为参数对象，因为我们不确定会得多少个，所以对他进行遍历
    public static ResultSet execute(Connection con,PreparedStatement pst, ResultSet result,String sql,Object [] params) throws SQLException{
        pst = con.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            //setObject 占位符从1开始，数组是从0开始的
            pst.setObject(i+1,params[i]);
        }
        return pst.executeQuery();
    }

    //2.DDL
    public static int execute(Connection con, PreparedStatement pst,String sql,Object [] params) throws SQLException{
        pst = con.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            //setObject 占位符从1开始，数组是从0开始的
            pst.setObject(i+1,params[i]);
        }
        return pst.executeUpdate();
    }

    //3.释放资源
    public static boolean CloseConnection(Connection con, PreparedStatement pst, ResultSet rst) {
        boolean isFlag = true;
        if (con != null) {
            try {
                con.close();
                //通知GC回收资源
                con = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                isFlag = false;
            }
        }
        if (pst != null) {
            try {
                pst.close();
                //通知GC回收资源
                pst = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                isFlag = false;
            }
        }
        if (rst != null) {
            try {
                rst.close();
                //通知GC回收资源
                rst = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                isFlag = false;
            }
        }
        return isFlag;
    }

}

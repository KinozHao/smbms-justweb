package com.market.dao;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author kinoz
 * @Date 2022/7/8 - 10:56
 * @apiNote 数据库操纵的公共类
 */
public class BaseDao {
    private static DataSource dataSource =null;
    static {
        Properties properties = new Properties();
        InputStream ins = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(ins);

             dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //1.获取数据库连接
    public static Connection getConnection() throws Exception{
        return dataSource.getConnection();
    }

    //2.公共查询方法
    public static ResultSet execute(Connection con,PreparedStatement pst, ResultSet result,String sql,Object [] params  ) throws SQLException{
        //预编译后米娜直接执行
        pst = con.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            //setObject 占位符从1开始，数组是从0开始的
            pst.setObject(i+1,params[i]);
        }
        result = pst.executeQuery();
        return result;
    }

    //2.公共增删改方法
    public static int execute(Connection con,String sql,Object [] params, PreparedStatement pst) throws SQLException{
        pst = con.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            //setObject 占位符从1开始，数组是从0开始的
            pst.setObject(i+1,params[i]);
        }
        int update = pst.executeUpdate();
        return update;
    }

    public static boolean CloseConnection(Connection con, Statement sta, ResultSet rst) {
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
        if (sta != null) {
            try {
                sta.close();
                //通知GC回收资源
                sta = null;
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

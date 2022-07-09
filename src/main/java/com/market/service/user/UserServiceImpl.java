package com.market.service.user;

import com.market.dao.BaseDao;
import com.market.dao.user.UserDao;
import com.market.dao.user.UserDaoImpl;
import com.market.entity.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author kinoz
 * @Date 2022/7/8 - 15:35
 * @apiNote
 */
public class UserServiceImpl implements UserService{
    //获取DAO里面的操纵对象,为后面调用Login方法做准备
    private final UserDao userDao;
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }


    //业务层引入DAO层并调用
    @Override
    //用户登录
    public User Login(String userCode, String password) {
        Connection con = null;
        User user = null;
        try {
            //获取连接，确定要登录对象
            con = BaseDao.getConnection();
            user = userDao.getLoginUser(con,userCode);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //释放资源
            BaseDao.CloseConnection(con,null,null);
        }
        return user;
    }

    @Override
    //修改当前用户密码
    public boolean updatePwd(long id, String password) {
        boolean flag = false;
        Connection con = null;

        con = BaseDao.getConnection();

        try {
            if (userDao.updatePwd(con,id,password) > 0) {
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.CloseConnection(con,null,null);
        }
        return flag;
    }

    /*@Test
    public void test(){
        UserServiceImpl us = new UserServiceImpl();
        User admin = us.Login("admin", "123");
        System.out.println(admin.getUserpassword());
    }*/
}

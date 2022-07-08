package com.market.service.user;

import com.market.dao.BaseDao;
import com.market.dao.user.UserDao;
import com.market.dao.user.UserDaoImpl;
import com.market.entity.User;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author kinoz
 * @Date 2022/7/8 - 15:35
 * @apiNote
 */
public class UserServiceImpl implements UserService{
    //获取DAO里面的操纵对象,为后面调用Login方法做准备
    private UserDao userDao;
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }


    // 业务层需调用dao层 引入dao层
    @Override
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

    /*@Test
    public void test(){
        UserServiceImpl us = new UserServiceImpl();
        User admin = us.Login("admin", "erewr");
        System.out.println(admin.getUserpassword());
    }*/
}

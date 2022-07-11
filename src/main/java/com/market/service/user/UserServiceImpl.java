package com.market.service.user;

import com.market.dao.BaseDao;
import com.market.dao.user.UserDao;
import com.market.dao.user.UserDaoImpl;
import com.market.entity.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kinoz
 * @Date 2022/7/8 - 15:35
 * @apiNote 业务层引入DAO层并调用
 */
public class UserServiceImpl implements UserService{
    //获取DAO里面的操纵对象,为后面调用Login方法做准备
    private final UserDao userDao;
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }

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
        //密码校验
        if (user != null){
            if (!user.getUserpassword().equals(password)){
                user = null;
            }
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

    @Override
    public int getUserCount(String QueryUserName, int QueryUserRole) {
        Connection con = null;
        int count = 0;
        try {
            con = BaseDao.getConnection();
            count = userDao.getUserCount(con, QueryUserName, QueryUserRole);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.CloseConnection(con,null,null);
        }
        return count;
    }

    @Override
    public List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize) {
        Connection con = null;
        List<User> userList = null;
        try {
            con = BaseDao.getConnection();
            userList = userDao.getUserList(con, userName, userRole, currentPageNo, pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.CloseConnection(con,null,null);
        }
        return userList;
    }

    /*@Test
    public void Test1(){
        //测试是否可以成功修改密码
        UserServiceImpl use = new UserServiceImpl();
        boolean b = use.updatePwd(2, "111119");
        System.out.println("用户密码修改:"+b);
    }
    @Test
    public void Test2(){
        UserServiceImpl us = new UserServiceImpl();
        User admin = us.Login("admin", "123");
        System.out.println(admin.getUserpassword());
    }
    @Test
    public void Test3(){
        UserServiceImpl use = new UserServiceImpl();
        System.out.println(use.getUserCount(null, 3));
    }*/

      /*@Test
    public void Test4(){
        UserServiceImpl us = new UserServiceImpl();
        System.out.println(us.getUserList(null, 3, 2, 3));
    }*/
}

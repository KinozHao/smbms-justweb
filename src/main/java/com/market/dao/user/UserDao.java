package com.market.dao.user;

import com.market.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kinoz
 * @Date 2022/7/8 - 15:01
 * @apiNote
 */
public interface UserDao {
    //得到要登陆的用户
    User getLoginUser(Connection con,String userCode) throws SQLException;

    //修改登录用户密码
    int updatePwd(Connection con,long id,String password) throws SQLException;

    //条件查询-用户总数
    int getUserCount(Connection con,String userName,int userRole)throws SQLException;

    //条件查询-用户列表
    List<User> getUserList(Connection con,String userName,int userRole,int currentPageNo,int pageSize)throws SQLException;

    //添加用户
    int addUser(Connection con,User user)throws SQLException;

    //删除用户 根据用户id
    int delUser(Connection con,Long delID)throws SQLException;

    //修改用户信息
    int modify(Connection con,User user) throws Exception;

    //查看用户信息 根据用户id
    User getUserById(Connection con, String id)throws SQLException;



}

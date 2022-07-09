package com.market.dao.user;

import com.market.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author kinoz
 * @Date 2022/7/8 - 15:01
 * @apiNote
 */
public interface UserDao {
    //得到要登陆的用户
    User getLoginUser(Connection con,String userCode) throws SQLException;

    //修改用户密码
    int updatePwd(Connection con,long id,String password) throws SQLException;
}

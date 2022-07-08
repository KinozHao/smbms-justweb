package com.market.dao.user;

import com.market.entity.User;

import java.sql.Connection;

/**
 * @author kinoz
 * @Date 2022/7/8 - 15:08
 * @apiNote
 */
public class UserDaoImpl implements UserDao{
    @Override
    public User getLoginUser(Connection con, String userCode) {

        String sql = "select * from smbms.smbms_user where userCode=?";
        Object[] params = {userCode}


        return null;
    }
}

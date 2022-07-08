package com.market.dao.user;

import com.market.dao.BaseDao;
import com.market.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author kinoz
 * @Date 2022/7/8 - 15:08
 * @apiNote
 */
public class UserDaoImpl implements UserDao{
    @Override
    public User getLoginUser(Connection con, String userCode) throws SQLException {
        PreparedStatement pst = null;
        ResultSet result = null;
        User user = null;

        //判断数据库连接是否成功
        if (con != null){
            String sql = "select * from smbms_user where userCode=?";
            Object[] params = {userCode};

            result = BaseDao.execute(con, pst, result, sql, params);
            if (result.next()){
                //用户信息关联数据库
                user = new User();
                user.setId(result.getLong("id"));
                user.setUsercode(result.getString("userCode"));
                user.setUserName(result.getString("userName"));
                user.setUserpassword(result.getString("userPassword"));
                user.setGender(result.getInt("gender"));
                user.setBirthday(result.getDate("birthday"));
                user.setPhone(result.getString("phone"));
                user.setAddress(result.getString("address"));
                user.setUserrole(result.getInt("userRole"));
                user.setCreatedby(result.getLong("createdBy"));
                user.setCreationdate(result.getDate("creationDate"));
                user.setModifyby(result.getLong("modifyBy"));
                user.setModifydate(result.getDate("modifyDate"));
            }

            //连接暂时不关闭
            BaseDao.CloseConnection(null,pst,result);
        }
        //若以上执行成功返回user信息
        return user;
    }
}

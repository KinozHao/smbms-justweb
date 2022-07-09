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
    //得到要登陆的用户
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
                //为实体类User里面的属性和SQL进行结合
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

            //连接会存在业务，暂时不关闭
            BaseDao.CloseConnection(null,pst,result);
        }
        //若以上执行成功返回user信息
        return user;
    }

    @Override
    //修改当前用户密码
    public int updatePwd(Connection con, long id, String password) throws SQLException {
        PreparedStatement pstm = null;
        int execute = 0;
        if (con != null){
            String sql = "update smbms_user set userPassword=? where id=?";
            Object[] param = {password,id};
            execute = BaseDao.execute(con, pstm, sql, param);
            BaseDao.CloseConnection(null,pstm,null);
        }

        return execute;
    }


}

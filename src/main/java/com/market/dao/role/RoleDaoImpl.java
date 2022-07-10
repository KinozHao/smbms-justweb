package com.market.dao.role;

import com.market.dao.BaseDao;
import com.market.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kinoz
 * @Date 2022/7/10 - 16:56
 * @apiNote
 */
public class RoleDaoImpl implements RoleDao{
    //获取角色列表
    @Override
    public List<Role> getRoleList(Connection con) throws SQLException {
        PreparedStatement state = null;
        ResultSet result = null;
        List<Role> roleList = new ArrayList<>();
        if (con != null){
            String sql = "select * from smbms_role";
            Object[] param = {};

            result = BaseDao.execute(con,state,result,sql,param);
            while (result.next()){
                Role role = new Role();
                role.setId(result.getLong("id"));
                role.setRolecode(result.getString("roleCode"));
                role.setRoleName(result.getString("roleName"));
                roleList.add(role);
            }
            BaseDao.CloseConnection(null,state,result);
        }
        return roleList;
    }
}

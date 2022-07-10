package com.market.dao.role;

import com.market.entity.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kinoz
 * @Date 2022/7/10 - 16:56
 * @apiNote
 */
public interface RoleDao {

    //获取角色列表
    List<Role> getRoleList(Connection con)throws SQLException;
}

package com.market.service.role;

import com.market.dao.BaseDao;
import com.market.dao.role.RoleDao;
import com.market.dao.role.RoleDaoImpl;
import com.market.dao.user.UserDao;
import com.market.dao.user.UserDaoImpl;
import com.market.entity.Role;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author kinoz
 * @Date 2022/7/10 - 16:59
 * @apiNote
 */
public class RoleServiceImpl implements RoleService {
    //获取DAO里面的操纵对象,为后面调用Login方法做准备
    private final RoleDao roleDao;
    public RoleServiceImpl(){
        roleDao = new RoleDaoImpl();
    }
    @Override
    public List<Role> getRoleList() {
        List<Role> roleList = null;
        Connection con = null;
        try {
            con = BaseDao.getConnection();
            roleList = roleDao.getRoleList(con);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.CloseConnection(con,null,null);
        }
        return roleList;
    }

    @Test
    public void test1(){
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        roleList.forEach(System.out::println);
    }
}

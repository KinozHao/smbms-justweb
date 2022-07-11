package com.market.service.provider;

import com.market.dao.BaseDao;
import com.market.dao.provider.ProviderDao;
import com.market.dao.provider.ProviderDaoImpl;
import com.market.dao.role.RoleDao;
import com.market.dao.role.RoleDaoImpl;
import com.market.entity.Provider;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kinoz
 * @Date 2022/7/11 - 9:45
 * @apiNote
 */
public class ProviderServiceImpl implements ProviderService{
    //获取DAO里面的操纵对象,为后面调用Login方法做准备
    private final ProviderDao providerDao;
    public ProviderServiceImpl(){
        providerDao = new ProviderDaoImpl();
    }

    @Override
    public List<Provider> getProviderList(String proName, String proCode) {
        Connection con = null;
        List<Provider> providerList = null;
        System.out.println("query proName ---- > " + proName);
        System.out.println("query proCode ---- > " + proCode);
        try {
            con = BaseDao.getConnection();
            providerList = providerDao.getProviderList(con, proName, proCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.CloseConnection(con,null,null);
        }

        return providerList;
    }

    // TODO: 2022/7/11 测试proName查询有问题
    // TODO: 2022/7/11 已解决 sql语句第一句删除;截断 
    @Test
    public void Test(){
        ProviderServiceImpl pss = new ProviderServiceImpl();
        List<Provider> bei = pss.getProviderList("北京", null);
        bei.forEach(System.out::println);
    }
}

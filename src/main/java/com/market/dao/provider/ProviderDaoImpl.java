package com.market.dao.provider;

import com.market.dao.BaseDao;
import com.market.entity.Provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kinoz
 * @Date 2022/7/11 - 9:33
 * @apiNote 供应商实现类
 */
public class ProviderDaoImpl implements ProviderDao {
    @Override
    public List<Provider> getProviderList(Connection con, String proName, String proCode) throws SQLException {
        PreparedStatement state = null;
        ResultSet result = null;
        List<Provider> providerList = new ArrayList<>();
        if (con != null){
            StringBuilder sql = new StringBuilder();
            sql.append("select * from smbms_provider where 1=1");
            List<Object> list = new ArrayList<>();
            if (proName != null){
            //if (!StringUtils.isNullOrEmpty(proName)){
                sql.append(" and proName like ?");
                list.add("%"+proName+"%");
            }

            if (proCode != null){
                sql.append(" and proCode like ?");
                list.add("%"+proCode+"%");
            }

            Object[] param = list.toArray();
            String change_sql = sql.toString();
            System.out.println("sqltext ---->"+change_sql);

            result = BaseDao.execute(con, state, result, change_sql, param);
            while (result.next()){
                Provider provider = new Provider();
                provider.setId(result.getLong("id"));
                provider.setProCode(result.getString("proCode"));
                provider.setProName(result.getString("proName"));
                provider.setProdesc(result.getString("proDesc"));
                provider.setProContact(result.getString("proContact"));
                provider.setProPhone(result.getString("proPhone"));
                provider.setProAddress(result.getString("proAddress"));
                provider.setProFax(result.getString("proFax"));
                provider.setCreationDate(result.getTimestamp("creationDate"));
                providerList.add(provider);
            }
            BaseDao.CloseConnection(null,state,result);
        }
        return providerList;
    }
}

package com.market.dao.provider;

import com.market.entity.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kinoz
 * @Date 2022/7/11 - 9:32
 * @apiNote
 */
public interface ProviderDao {
    //通过供应商名称、编码获取供应商列表-模糊查询-providerList
    List<Provider> getProviderList(Connection con,String proName,String proCode) throws SQLException;
}

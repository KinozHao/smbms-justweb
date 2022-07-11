package com.market.service.provider;

import com.market.entity.Provider;

import java.sql.Connection;
import java.util.List;

/**
 * @author kinoz
 * @Date 2022/7/11 - 9:45
 * @apiNote
 */
public interface ProviderService {
    //通过供应商名称、编码获取供应商列表-模糊查询-providerList
    List<Provider> getProviderList(String proName, String proCode);
}

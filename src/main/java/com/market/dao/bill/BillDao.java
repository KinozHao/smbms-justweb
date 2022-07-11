package com.market.dao.bill;

import com.market.entity.Bill;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kinoz
 * @Date 2022/7/11 - 14:15
 * @apiNote
 */
public interface BillDao {
    //通过供应商名称、编码
    //获取供应商列表
    List<Bill> getBillList(Connection con, Bill bill) throws SQLException;
}

package com.market.service.bill;

import com.market.entity.Bill;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kinoz
 * @Date 2022/7/11 - 14:30
 * @apiNote
 */
public interface BillService {
    //通过供应商名称、编码
    //获取供应商列表
    List<Bill> getBillList(Bill bill);
}

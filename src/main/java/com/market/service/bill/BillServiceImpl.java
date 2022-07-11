package com.market.service.bill;

import com.market.dao.BaseDao;
import com.market.dao.bill.BillDao;
import com.market.dao.bill.BillDaoImpl;
import com.market.entity.Bill;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kinoz
 * @Date 2022/7/11 - 14:31
 * @apiNote
 */
public class BillServiceImpl implements BillService{
    //获取DAO的操纵对象
    public final BillDao billDao;
    public BillServiceImpl() {
        billDao = new BillDaoImpl();
    }

    @Override
    public List<Bill> getBillList(Bill bill){
        Connection con = null;
        List<Bill> billList = null;
        try {
            //1.获取连接
            con = BaseDao.getConnection();
            //2.调用DAO方法
            billList = billDao.getBillList(con, bill);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //3.返回查询结果
        return billList;
    }

}

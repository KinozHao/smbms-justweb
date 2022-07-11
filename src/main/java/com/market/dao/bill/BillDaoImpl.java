package com.market.dao.bill;

import com.market.dao.BaseDao;
import com.market.entity.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author kinoz
 * @Date 2022/7/11 - 14:16
 * @apiNote 调用数据库公共类
 * 联表查询smbms_bill和smbms_provider
 * 反馈数据到Service层
 */
public class BillDaoImpl implements BillDao {
    @Override
    public List<Bill> getBillList(Connection con, Bill bill) throws SQLException {
        PreparedStatement state = null;
        ResultSet result = null;
        //此bill_list用于最终方法返回的结果集
        List<Bill> bill_list = new ArrayList<>();

        if (con != null){
            //存储sql中?对应的变量
            List<Object> param = new Vector<>();

            //用于sql的拼接
            StringBuilder sql = new StringBuilder();
            sql.append("select b.*,p.proName as providerName from smbms_bill b, smbms_provider p where b.providerId = p.id");

            //判断商品名称
            if (bill.getProductName() != null){
                sql.append(" and productName like ?");
                param.add("%"+bill.getProductName()+"%");
            }
            //判断供应商id
            if (bill.getProviderId() >0){
                sql.append(" and providerId = ?");
                param.add(bill.getProviderId());
            }
            //判断是否支付
            if (bill.getIsPayment() > 0){
                sql.append(" and isPayment = ?");
                param.add(bill.getIsPayment());
            }

            //类型转换,存放到公共查询方法中
            Object[] change_param = param.toArray();
            String change_sql = sql.toString();
            System.out.println("SQL----->"+change_sql);
            result = BaseDao.execute(con,state,result,change_sql,change_param);
            //查到的数据和entity实体类对应
            while (result.next()){
                Bill zd = new Bill();
                zd.setId(result.getLong("id"));
                zd.setBillCode(result.getString("billCode"));
                zd.setProductName(result.getString("productName"));
                zd.setProductDesc(result.getString("productDesc"));
                zd.setProductUnit(result.getString("productUnit"));
                zd.setProductCount(result.getBigDecimal("productCount"));
                zd.setTotalPrice(result.getBigDecimal("totalPrice"));
                zd.setIsPayment(result.getInt("isPayment"));
                zd.setProviderId(result.getInt("providerId"));
                zd.setProviderName(result.getString("providerName"));
                zd.setCreationDate(result.getTimestamp("creationDate"));
                zd.setCreatedBy(result.getLong("createdBy"));
                //添加至返回用的结果集
                bill_list.add(zd);
            }
        }
        //返回查询参数
        return bill_list;
    }
}

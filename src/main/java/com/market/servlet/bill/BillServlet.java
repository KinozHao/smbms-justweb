package com.market.servlet.bill;

import com.market.entity.Bill;
import com.market.entity.Provider;
import com.market.service.bill.BillServiceImpl;
import com.market.service.provider.ProviderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kinoz
 * @Date 2022/7/11 - 14:39
 * @apiNote
 */
public class BillServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取前端参数
        String method = req.getParameter("method");
        //判断参数对应的value,写对应的方法
        if (method.equals("query") && method != null){
            query(req, resp);
        }
    }

    private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProviderServiceImpl providerService = new ProviderServiceImpl();
        List<Provider> providerList = providerService.getProviderList("","");
        req.setAttribute("providerList",providerList);

        //获取前端参数并和Entity类中数据进行匹配
        String queryIsPayment = req.getParameter("queryIsPayment");
        String queryProductName = req.getParameter("queryProductName");
        String queryProviderId = req.getParameter("queryProviderId");
        if (queryProductName != null){
            queryProductName = "";
        }
        Bill bill = new Bill();
        if (queryIsPayment == null){
            bill.setIsPayment(0);
        }else {
            bill.setIsPayment(Integer.parseInt(queryIsPayment));
        }
        if (queryProviderId == null){
            bill.setProviderId(0);
        }else {
            bill.setProviderId(Integer.parseInt(queryProviderId));
        }
        bill.setProductName(queryProductName);


        //最终获取到的billList拿一个新的数据进行存放
        List<Bill> billList = new BillServiceImpl().getBillList(bill);

        //后端参数和前端参数键值对应
        req.setAttribute("billList",billList);
        req.setAttribute("queryProductName",queryProductName);
        req.setAttribute("queryProviderId",queryProviderId);
        req.setAttribute("queryIsPayment",queryIsPayment);

        //请求转发到对应页面
        req.getRequestDispatcher("billlist.jsp").forward(req,resp);
    }

}

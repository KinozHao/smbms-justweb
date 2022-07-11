package com.market.servlet.provider;

import com.market.entity.Provider;
import com.market.service.provider.ProviderServiceImpl;
import com.mysql.jdbc.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kinoz
 * @Date 2022/7/11 - 10:12
 * @apiNote
 */
public class ProviderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("query") && method != null){
            provider_Query(req, resp);

        }
    }

    private void provider_Query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String queryProName = req.getParameter("queryProName");
        String queryProCode = req.getParameter("queryProCode");
        if (StringUtils.isNullOrEmpty(queryProName)){
            queryProName = "";
        }
        if (StringUtils.isNullOrEmpty(queryProCode)){
            queryProCode = "";
        }

        List<Provider> providerList = new ProviderServiceImpl().getProviderList(queryProName, queryProCode);

        req.setAttribute("providerList",providerList);
        req.setAttribute("queryProName",queryProName);
        req.setAttribute("queryProCode",queryProCode);

        req.getRequestDispatcher("providerlist.jsp").forward(req,resp);
    }
}

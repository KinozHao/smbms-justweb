package com.market.servlet.user;

import com.market.entity.User;
import com.market.service.user.UserServiceImpl;
import com.market.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author kinoz
 * @Date 2022/7/9 - 14:34
 * @apiNote
 */
//实现servlet的复用
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("savepwd") && method !=null){
            updatePwd(req, resp);
        }

    }

    private void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object obj = req.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = req.getParameter("newpassword");

        boolean flag = false;

        if (obj!=null && newpassword!=null && newpassword.length() !=0){
            UserServiceImpl use = new UserServiceImpl();
            flag = use.updatePwd(((User)obj).getId(),newpassword);
            if (flag){
                req.setAttribute(Constants.POINT_MESSAGE,"修改成功,请退出重新登录");
                req.getSession().removeAttribute(Constants.USER_SESSION);
            }else {
                req.setAttribute(Constants.POINT_MESSAGE,"密码修改失败");
            }
        }else {
            req.setAttribute(Constants.POINT_MESSAGE,"新密码存在问题");
        }
        req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

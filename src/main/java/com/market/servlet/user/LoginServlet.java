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
 * @Date 2022/7/8 - 15:51
 * @apiNote 控制层调用业务层代码
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取前端用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        //2.调用业务层方法,和数据库校验密码
        UserServiceImpl usil = new UserServiceImpl();
        User user = usil.Login(userCode, userPassword);
        if (user != null){
            //若非空登录成功,携带一组session存放在服务器中
            req.getSession().setAttribute(Constants.USER_SESSION,user);
            //跳转到后台页面
            resp.sendRedirect("jsp/frame.jsp");
        }else {
            //在前端提示信息,请求转发到当前页面
            req.setAttribute("error","用户名或密码不正确!");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

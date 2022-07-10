package com.market.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.market.entity.User;
import com.market.service.user.UserServiceImpl;
import com.market.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
        }else if (method.equals("pwdmodify") && method !=null){
            pwdModify(req,resp);
        }

    }

    //更新密码
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

    //使用ajax与旧密码做校验
    private void pwdModify(HttpServletRequest req, HttpServletResponse resp){
        //从session中获取id
        Object obj = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");

        //结果集(此处多对应pwdmodify.js中的参数)
        Map<String, String> resultMap = new HashMap<>();
        if (obj == null){ //session失效或session过期
            resultMap.put("result","sessionerror");
        }else if (oldpassword == null){ //输入的密码为空
            resultMap.put("result","error");
        }else{
            String userpassword = ((User) obj).getUserpassword();   //session中用户的密码
            //输入的旧密码与session中存储的匹配的话执行
            if (oldpassword.equals(userpassword)){
                resultMap.put("result","true");
            //则否
            }else {
                resultMap.put("result","false");
            }
        }

        resp.setContentType("application/json");
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            //JSONArray Alibaba json工具类 转换格式
            //把map["result","true","result","false"]格式转换为json格式{key:value}
            out.write(JSONArray.toJSONString(resultMap));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}

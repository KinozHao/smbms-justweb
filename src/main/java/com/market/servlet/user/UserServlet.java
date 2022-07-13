package com.market.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.market.entity.Role;
import com.market.entity.User;
import com.market.service.role.RoleServiceImpl;
import com.market.service.user.UserService;
import com.market.service.user.UserServiceImpl;
import com.market.util.Constants;
import com.market.util.PageSupport;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.desktop.UserSessionEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("add") && method != null){
            addUser(req,resp);
        }else if (method.equals("savepwd") && method !=null){
            updatePwd(req, resp);
        }else if (method.equals("pwdmodify") && method !=null){
            pwdModify(req,resp);
        }else if (method.equals("query") && method != null){
            query(req,resp);
        }else if (method.equals("deluser") && method != null){
            delUser(req,resp);
        }else if (method.equals("view") && method != null) {
            getUserById(req, resp);
        }else if (method.equals("modifyexe") && method != null){
            modify(req,resp);
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

    //用户分页
    private void query(HttpServletRequest req, HttpServletResponse resp) {
        //1.从前端获取数据
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");
        int queryUserRole = 0;
        List<User> userList = null; //获取用户列表做前端展示

        //2.判断请求是否需要处理
        //获取用列表
        UserServiceImpl user = new UserServiceImpl();
        int pageSize =5;
        int currentPageNo = 1;
        if (queryUserName == null){
            queryUserName="";
        }
        if (temp != null && !temp.equals("")){
             queryUserRole = Integer.parseInt(temp);
        }

        if (pageIndex!= null){
           currentPageNo =  Integer.parseInt(pageIndex);
        }

        //3.为了实现分页,需要计算出当前页面和总页面,页面大小
        //获取用户总数 (分页:上一页 下一页情况)
        int totalCount = user.getUserCount(queryUserName,queryUserRole);
        //总页数支持
        PageSupport pst = new PageSupport();
        pst.setCurrentPageNo(currentPageNo);
        pst.setPageSize(pageSize);
        pst.setTotalCount(totalCount);

        //总共有几页
        int totalPageCount = pst.getTotalPageCount();   //使用狂神工具类
        //int totalPageCount = totalCount/pageSize +1;   //手动计算

        //4.控制首页和尾页(相当于业务需求)
        //页面小于1就显示第一页的东西
        if (currentPageNo < 1) {
            currentPageNo = 1;
        //当前页面大于了最后一页就让它等于最后一页
        }else if (currentPageNo>totalCount){
            currentPageNo = totalCount;
        }

        //5.获取用户列表展示到前端
        userList = user.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
        req.setAttribute("userList",userList);
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        //后台数据与前端的value对应
        req.setAttribute("roleList",roleList);
        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",totalPageCount);
        req.setAttribute("queryUserName",queryUserName);
        req.setAttribute("queryUserRole",queryUserRole);

        try {
            req.getRequestDispatcher("userlist.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //添加用户
    // TODO: 2022/7/13 无法提交用户表单 用户角色设置无效 
    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String userCode = req.getParameter("userCode");
        String userName  = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        String gender= req.getParameter("gender");
        String birthday= req.getParameter("birthday");
        String phone= req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole= req.getParameter("userRole");

        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserpassword(userPassword);
        user.setAddress(address);
        try {
            user.setBirthday(new SimpleDateFormat("yyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setGender(Integer.valueOf(gender));
        user.setPhone(phone);
        user.setUserrole(Integer.valueOf(userRole));
        user.setCreationdate(new Date());
        user.setCreatedby(((User)req.getSession().getAttribute(Constants.USER_SESSION)).getId());

        UserServiceImpl use = new UserServiceImpl();
        if (use.addUser(user)){
            resp.sendRedirect(req.getContextPath()+"/jsp/user.do?method=query");
        }else {
            req.getRequestDispatcher("useradd.jsp").forward(req,resp);
        }
    }

    //删除用户
    private void delUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("uid");
        int delId = 0;
        try {
            delId = Integer.parseInt(id);
        }catch (Exception e){
            delId = 0;
        }

        HashMap<String, String> resultMap = new HashMap<>();
        if (delId<=0){
            resultMap.put("delResult","notexist");
        }else {
            UserServiceImpl use = new UserServiceImpl();
            if (use.delUser((long) delId)){
                resultMap.put("delResult","true");
            }else {
                resultMap.put("delResult","false");
            }
        }
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.write(JSONArray.toJSONString(resultMap));
        out.flush();
        out.close();
    }

    //修改用户信息
    // TODO: 2022/7/13 无法跳转到修改页面
    private void modify(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id = req.getParameter("uid");
        String userName = req.getParameter("userName");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");

        User user = new User();
        user.setId(Integer.valueOf(id));
        user.setUserName(userName);
        user.setGender(Integer.parseInt(gender));
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserrole(Integer.parseInt(userRole));
        //通过session判定谁操作修改的此信息
        user.setModifyby(((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        user.setModifydate(new Date());

        UserServiceImpl userService = new UserServiceImpl();
        if (userService.modify(user)){
            resp.sendRedirect(req.getContextPath()+"/jsp/user.do?method=query");
        }else {
            req.getRequestDispatcher("usermodify.jsp").forward(req,resp);
        }
    }

    //查询用户信息
    private void getUserById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("uid");
        if (id != null){
            UserServiceImpl use = new UserServiceImpl();
            User user = use.getUserByID(id);
            req.setAttribute("user",user);
            req.getRequestDispatcher("userview.jsp").forward(req,resp);
        }
    }
}

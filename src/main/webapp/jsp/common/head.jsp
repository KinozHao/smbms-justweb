<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/1/27
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>控制台-超市订单管理系统</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/statics/images/favicon.png">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/style.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/public.css" />
</head>
<body>
<!--头部-->
<header class="publicHeader">
    <h1>超市订单管理系统</h1>
    <div class="publicHeaderR">
        <p><span>下午好！</span><span style="color: #fff21b">${userSession.userName}</span> , 欢迎你！</p>
        <a href="${pageContext.request.contextPath }/logout.do">退出</a>
    </div>
</header>
<!--时间-->
<section class="publicTime">
    <span id="time">2015年1月1日 11:11  星期一</span>
    <a href="https://www.google.cn/chrome/index.html">温馨提示：使用Chrome浏览器，访问速度大幅度提高！（点击跳转）</a>
</section>
<!--主体内容-->
<section class="publicMian ">
    <div class="left">
        <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
        <nav>
            <ul class="list">
                <li><a href="${pageContext.request.contextPath }/jsp/bill.do?method=query">订单管理</a></li>
                <li><a href="${pageContext.request.contextPath }/jsp/provider.do?method=query">供应商管理</a></li>
                <li><a href="${pageContext.request.contextPath }/jsp/user.do?method=query">用户管理</a></li>
                <li><a href="${pageContext.request.contextPath }/jsp/pwdmodify.jsp">密码修改</a></li>
                <li><a href="${pageContext.request.contextPath }/logout.do">退出系统</a></li>
            </ul>
        </nav>
    </div>
    <input type="hidden" id="path" name="path" value="${pageContext.request.contextPath }"/>
    <input type="hidden" id="referer" name="referer" value="<%=request.getHeader("Referer")%>"/>


﻿<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>系统登录 --聚旺福超市订单管理系统</title>
    <link type="text/css" rel="stylesheet" href="css/style.css" />
    <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="js/jquery.cookie.js"></script>
    <script type="text/javascript">
    	$(function(){
    		$("#login").click(function(){
    			var userCode = $("#userCode").val();
    			var password = $("#userPassword").val();
    			$.post("user/gologin",{"userCode":userCode,"password":password},function(result){
    				if(result != null){
    					if(result.flag==0){ //登录成功
    						//保存用户名称到cookie,以便自动登录系统
    						var date = new Date();
    					  	//获取过期时间（单位：毫秒）
    						date.setTime(date.getTime()+parseInt($("#time").val())*1000);
    						//保存cookie信息
    						$.cookie("usercode",result.data.usercode,{expires:date,path:"/"});
    						//跳转到首页
    						location.href="user/frame";
    					}else{ //登录失败
    						//显示失败信息
    						$(".info").html(result.msg);
    					}
    				}
    			},"json");
    		})
    	})
    </script>
</head>
<body class="login_bg">
    <section class="loginBox">
        <header class="loginHeader">
            <h1>聚旺福超市订单管理系统</h1>
        </header>
        <section class="loginCont">
	        <form class="loginForm" action="jsp/frame.jsp"  name="actionForm" id="actionForm"  method="post" >
				<div class="info">${error}</div>
                <div class="inputbox">
                    <label>用户名：</label>
					<input type="text" class="input-text" id="userCode" name="userCode" placeholder="请输入用户名"/>
				</div>	
				<div class="inputbox">
                    <label>密码：</label>
                    <input type="password" id="userPassword" name="userPassword" placeholder="请输入密码"/>
                </div>
				<div class="subBtn">
                    <input type="button" id="login" value="登录"/>
                    <input type="reset" value="重置"/>
                </div>	
			</form>
        </section>
    </section>
</body>
</html>

## 超市订单管理系统
本项目基于javaWeb+jsp+Mysql+Tomcat+前端。

教程:[狂神说:JavaWeb](https://www.bilibili.com/video/BV12J411M7Sj?p=30)
### 开发环境
1. IDEA v2020.1.3
2. Tomcat v10.0.22
3. Maven v3.6.3
4. Mysql v5.7.20
5. git v2.35.1

### 学习记录
+ 2022-7-5 环境配置成功完成项目的的entity类
+ 2022-7-6 完成登录注销功能、中文乱码Filter、未登录禁止访问内部页面SysFilter
+ 2022-7-8 完成修改密码功能
+ 2022-7-10 完成用户管理页面的查询、分页、跳转功能
+ 2022-7-11 完成订单管理页面查询、用户登录密码校验


### 其他
+ 狂神讲解采用的Tomcat为v9此项目采用的为Tomcat10,Maven的依赖和教程有所不同请留意
+ 项目需要的Mysql创表语句存放在src/resources下
+ maven配置和maven仓库都存在jdbc驱动的情况下tomcat运行后报错,解决方案在tomcat的lib目录下添加jdbc驱动jar包
